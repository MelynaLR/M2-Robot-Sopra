package com.soprasteria.jira.agile.webapp.infrastructure;

import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.soprasteria.jira.agile.webapp.models.Issue;
import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.json.JSONArray;

@Component
public class ChatGPTClient {
	
	@Value("${chatgpt.token}")
	private String API_KEY;
	
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String MODEL_IDENTIFIER = "gpt-4"; // OR gpt-3.5-turbo
    
    public String generateRecommendation(List<Issue> issues, List<String> additionalInstructions) {
    	System.out.println("token: "+API_KEY);
        //OkHttpClient client = new OkHttpClient();
    	/*
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(60, TimeUnit.SECONDS) // Adjust the timeout value as needed
                .build();
        */
    	OkHttpClient client = new OkHttpClient.Builder()
    	        .connectTimeout(60, TimeUnit.SECONDS)
    	        .writeTimeout(120, TimeUnit.SECONDS)
    	        .readTimeout(60, TimeUnit.SECONDS)
    	        .build();
        
        // Prepare the input prompt
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("Act as a professional in agile methodology counseling, and a professional in Jira. Given the following Jira issues, analyze the team's application of Agile methodology and provide recommendations for improvement: \n");

        for (Issue issue : issues) {
            promptBuilder.append("- ").append(issue.getDescription()).append(" with ").append(issue.getUserPoints()).append(" user points\n");
        }
        
        // Append additional instructions
        for (String instruction : additionalInstructions) {
            promptBuilder.append(instruction).append("\n");
        }
        
        
        String prompt = promptBuilder.toString();

        // Escape special characters in the user's content
        String escapedPrompt = StringEscapeUtils.escapeJson(prompt);

        // Prepare the JSON payload with the "model" parameter
        String json = String.format("{\"model\":\"%s\",\"messages\":[{\"role\":\"system\",\"content\":\"You are a helpful assistant.\"},{\"role\":\"user\",\"content\":\"%s\"}]}", MODEL_IDENTIFIER, escapedPrompt);

        // Print the JSON data for debugging
        System.out.println("JSON Data: " + json);

        // Build the request
        RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + API_KEY)
                .post(requestBody)
                .build();

        // Execute the request
        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                System.out.println("Response from ChatGPT API: " + responseBody);
                return extractRecommendation(responseBody);
            } else {
                System.err.println("Error from ChatGPT API: " + response.code() + " " + response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Error generating recommendation";
    }

    private static String extractRecommendation(String responseBody) {
        try {
            // Parse the JSON response
            JSONObject jsonResponse = new JSONObject(responseBody);

            // Retrieve the recommendation from the JSON
            JSONArray choices = jsonResponse.getJSONArray("choices");
            JSONObject firstChoice = choices.getJSONObject(0);
            JSONObject message = firstChoice.getJSONObject("message");
            String recommendation = message.getString("content");

            // Return the extracted recommendation
            return recommendation;
        } catch (Exception e) {
            // Handle any parsing errors
            e.printStackTrace();
            return "Error extracting recommendation";
        }
    }
    
    //Method allowing appending additional instructions to the prompt sent to chatGPT 
    public static List<String> promptEngineering(List<String> additionalInstructions) {
    	
		 additionalInstructions.add("Emphasize recommendations that align with Agile principles.");
		 additionalInstructions.add("Consider team collaboration and communication in your recommendations.");
		 additionalInstructions.add("Prioritize recommendations for continuous improvement in the Agile process.");
		 additionalInstructions.add("Highlight best practices in Agile methodology and Jira usage.");
		 additionalInstructions.add("Provide suggestions to address any technical debt identified in the issues.");
		 additionalInstructions.add("Consider the team's skills and expertise in your recommendations.");
		 additionalInstructions.add("Pay attention to user story points and their impact on project progress.");
		 additionalInstructions.add("Suggest additional Agile tools or integrations that may benefit the team.");
		 additionalInstructions.add("Evaluate the effectiveness of sprint planning in addressing the Jira issues.");
		 return additionalInstructions;
    }
    
}
