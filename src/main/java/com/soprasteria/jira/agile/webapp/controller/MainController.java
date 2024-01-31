package com.soprasteria.jira.agile.webapp.controller;

import org.springframework.web.bind.annotation.RestController;

import com.soprasteria.jira.agile.webapp.services.ScoreCalculation;
import com.soprasteria.jira.agile.webapp.services.rules.HighComplexityTicketRule;
import com.soprasteria.jira.agile.webapp.services.rules.DataAnalysisRule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soprasteria.jira.agile.webapp.apiHandler.ChatGPTClient;
import com.soprasteria.jira.agile.webapp.apiHandler.JiraAPI;
import com.soprasteria.jira.agile.webapp.infrastructure.DatabaseReader;
//import com.soprasteria.jira.agile.webapp.services.rules.TeamMemberAgilityManager;
//chatGPT query imports
import com.soprasteria.jira.agile.webapp.models.Issue;


import java.sql.SQLException;

import java.security.PublicKey;

import java.util.ArrayList;
import java.util.List; // Import List from java.util
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;





@RestController
public class MainController{
	@Autowired
	private JiraAPI jiraAPI;
	
	@Autowired
	private ChatGPTClient chatGPTClient;
	

	@Autowired
	private DatabaseReader databaseReader;

	@Autowired 
	private ScoreCalculation scoreCalculation;
	
	
	public static void main(String[] args) {
        MainController mainController = new MainController();

        mainController.retrieveDataSB();

		try {
            
			String url = "https://m2-projet-annuel-robot.atlassian.net/jira/software/projects/KAN/boards/1?isInsightsOpen=true";
            Document document = Jsoup.connect(url).get();

            // Extract data based on HTML structure
            Elements dataElements = document.select(".your-data-class");
            for (Element element : dataElements) {
                System.out.println(element.text());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	
	
	@GetMapping()
	public void retrieveDataSB() {
		jiraAPI.createAuthorizationHeader();
		String urlTest="https://m2-projet-annuel-robot.atlassian.net/rest/api/3/search?jql=";
		jiraAPI.sendRequestAPI(urlTest);
		
		//testing chatGPT query
		
		// Call DatabaseReader to retrieve issues from the database

        List<Issue> issues = databaseReader.readIssuesFromDatabase();

        //List<Issue> issues = DatabaseReader.readIssuesFromDatabase();
        List<String> additionalInstructions = new ArrayList<>();
		additionalInstructions =  ChatGPTClient.promptEngineering(additionalInstructions);


        // Call ChatGPTClient to generate recommendations based on the retrieved issues
        String recommendation = chatGPTClient.generateRecommendation(issues,additionalInstructions);

        // Print or use the recommendation as needed
        System.out.println("Recommendation from ChatGPT: " + recommendation);        
	}
	
	@GetMapping(value="/gpt/recommandations")
	public void gptRecommandations() {
		//jiraAPI.createAuthorizationHeader();

		List<Issue> issues = databaseReader.readIssuesFromDatabase();
		 //List<Issue> issues = DatabaseReader.readIssuesFromDatabase();
		 List<String> additionalInstructions = new ArrayList<>();
		additionalInstructions =  ChatGPTClient.promptEngineering(additionalInstructions);

        
		 // Call ChatGPTClient to generate recommendations based on the retrieved issues	       
		 String recommendation = chatGPTClient.generateRecommendation(issues,additionalInstructions);
	        
		 // Print or use the recommendation as needed	        
		 System.out.println("Recommendation from ChatGPT: " + recommendation);
	}
	
				
	@GetMapping(value="/globalScore")
	public void scoreResult() {
		scoreCalculation.getRules(databaseReader.readIssuesFromDatabase());
		//scoreCalculation.calculateGlobalScore();
	}
	
	
	@GetMapping(value = "/")
    public void retrieveData() {
        //System.out.println("coucou yannis");
        jiraAPI.createAuthorizationHeader();
        String urlTest = "https://m2-projet-annuel-robot.atlassian.net/rest/api/3/search?jql=";
        
        jiraAPI.sendRequestAPI(urlTest);
    }


	/*
	 * @GetMapping(value="/") public void retrieveData() {
	 * jiraAPI.createAuthorizationHeader(); String urlTest=
	 * "https://m2-projet-annuel-robot.atlassian.net/rest/api/3/search?jql=";
	 * jiraAPI.sendRequestAPI(urlTest);
	 * 
	 * //testing chatGPT query
	 * 
	 * // Call DatabaseReader to retrieve issues from the database List<Issue>
	 * issues = DatabaseReader.readIssuesFromDatabase();
	 * 
	 * // Call ChatGPTClient to generate recommendations based on the retrieved
	 * issues String recommendation = chatGPTClient.generateRecommendation(issues);
	 * 
	 * // Print or use the recommendation as needed
	 * System.out.println("Recommendation from ChatGPT: " + recommendation); }
	 */
}
