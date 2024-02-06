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

import com.soprasteria.jira.agile.webapp.infrastructure.ChatGPTClient;
import com.soprasteria.jira.agile.webapp.infrastructure.DatabaseReader;

import com.soprasteria.jira.agile.webapp.infrastructure.JiraAPI;

import com.soprasteria.jira.agile.webapp.services.rules.TeamMemberAgilityManagerRule;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;





@RestController
public class MainController{
	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
	
	@Autowired
	private JiraAPI jiraAPI;
	
	@Autowired
	private ChatGPTClient chatGPTClient;
	

	@Autowired
	private DatabaseReader databaseReader;

	@Autowired 
	private ScoreCalculation scoreCalculation;

	@Autowired 
	private HighComplexityTicketRule highComplexityTicketRule;
	
	
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
		System.out.println("Issues inserted into database");
		
		
		/*
		// Call DatabaseReader to retrieve issues from the database

       


        List<Issue> issues = databaseReader.readIssuesFromDatabase();

        //List<Issue> issues = DatabaseReader.readIssuesFromDatabase();
        List<String> additionalInstructions = new ArrayList<>();
		additionalInstructions =  ChatGPTClient.promptEngineering(additionalInstructions);

        // Call ChatGPTClient to generate recommendations based on the retrieved issues
        String recommendation = chatGPTClient.generateRecommendation(issues,additionalInstructions);

        // Print or use the recommendation as needed
        System.out.println("Recommendation from ChatGPT: " + recommendation);   
        */     

	}
	
	@GetMapping(value="/gpt/recommandations")
	public void gptRecommandations() {
		LOGGER.info("starting endpoint gpt recommandations");
		
		//jiraAPI.createAuthorizationHeader();

		List<Issue> issues = databaseReader.readIssuesFromDatabase();
		 //List<Issue> issues = DatabaseReader.readIssuesFromDatabase();
		 List<String> additionalInstructions = new ArrayList<>();
		additionalInstructions =  ChatGPTClient.promptEngineering(additionalInstructions);

        
		 // Call ChatGPTClient to generate recommendations based on the retrieved issues	       
		 String recommendation = chatGPTClient.generateRecommendation(issues,additionalInstructions);
	        
		 // Print or use the recommendation as needed	        
		LOGGER.info("Recommendation from ChatGPT: " + recommendation);
		LOGGER.info("end of the GPT recommandation");
	}
	
				
	@GetMapping(value="/globalScore")
	public int scoreResult() {
		scoreCalculation.getRules(databaseReader.readIssuesFromDatabase());
		return scoreCalculation.calculateGlobalScore();
	}
	@GetMapping(value="/score/userPoints")
    public int scorePointResult() {
        return highComplexityTicketRule.getScore();
	
	}
	/*
	
	@GetMapping(value = "/test")

    public void retrieveData() {
        
        jiraAPI.createAuthorizationHeader();
        String urlTest = "https://m2-projet-annuel-robot.atlassian.net/rest/api/3/search?jql=";
        
        jiraAPI.sendRequestAPI(urlTest);
    }
// 	@GetMapping(value="/score/userPoints")
// public int scoreResult() {
//     scoreCalculation.getRules(databaseReader.readIssuesFromDatabase());
//     return scoreCalculation.calculateGlobalScore();
// }

	
	// @GetMapping(value = "/")
    // public void retrieveData() {
    //     //System.out.println("coucou yannis");
    //     jiraAPI.createAuthorizationHeader();
    //     String urlTest = "https://m2-projet-annuel-robot.atlassian.net/rest/api/3/search?jql=";
        
    //     jiraAPI.sendRequestAPI(urlTest);
    // }

*/
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
