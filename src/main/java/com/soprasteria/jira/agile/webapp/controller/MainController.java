package com.soprasteria.jira.agile.webapp.controller;

import org.springframework.web.bind.annotation.RestController;

import com.soprasteria.jira.agile.webapp.services.JiraAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;


//chatGPT query imports
import com.soprasteria.jira.agile.webapp.models.Issue;
import com.soprasteria.jira.agile.webapp.services.DatabaseReader;
import com.soprasteria.jira.agile.webapp.services.ChatGPTClient;
import com.soprasteria.jira.agile.webapp.services.DataAnalysis;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List; // Import List from java.util



@RestController
public class MainController{
	@Autowired
	private JiraAPI jiraAPI;
	
	@Autowired
	private DataAnalysis dataAnalysis;
	
	@Autowired
	private ChatGPTClient chatGPTClient;
	

	@Autowired
	private DatabaseReader databaseReader;

	//@Autowired
	private List<Issue> issues = databaseReader.readIssuesFromDatabase();

	
	
	@GetMapping(value="/")
	public void globalAPI() {
		System.out.println("Contenu des tables");
		try {
			databaseReader.printTables();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		jiraAPI.createAuthorizationHeader();
		String urlTest="https://m2-projet-annuel-robot.atlassian.net/rest/api/3/search?jql=";
		jiraAPI.sendRequestAPI(urlTest);
		
		//testing chatGPT query
		
		// Call DatabaseReader to retrieve issues from the database

        List<Issue> issues = databaseReader.readIssuesFromDatabase();

        //List<Issue> issues = DatabaseReader.readIssuesFromDatabase();
        List<String> additionalInstructions = new ArrayList<>();;
		additionalInstructions =  ChatGPTClient.promptEngineering(additionalInstructions);


        // Call ChatGPTClient to generate recommendations based on the retrieved issues
        String recommendation = chatGPTClient.generateRecommendation(issues,additionalInstructions);

        // Print or use the recommendation as needed
        System.out.println("Recommendation from ChatGPT: " + recommendation);        
	}
	
	@GetMapping(value="/gpt/recommandations")
	public void gptRecommandations() {
		//jiraAPI.createAuthorizationHeader();

		 //List<Issue> issues = DatabaseReader.readIssuesFromDatabase();
		 List<String> additionalInstructions = new ArrayList<>();
		additionalInstructions =  ChatGPTClient.promptEngineering(additionalInstructions);

        
		 // Call ChatGPTClient to generate recommendations based on the retrieved issues	       
		 String recommendation = chatGPTClient.generateRecommendation(issues,additionalInstructions);
	        
		 // Print or use the recommendation as needed	        
		 System.out.println("Recommendation from ChatGPT: " + recommendation);
	}
	
	@GetMapping(value="/score/userPoints")
	public void scoreUserPoints() {
		ArrayList<Issue> issues = (ArrayList<Issue>) databaseReader.readIssuesFromDatabase();
		ArrayList<Issue> badIssues = dataAnalysis.filterBadIssues(issues);
		int score = dataAnalysis.calculateUserPoints(badIssues);
		System.out.println("Score de user points: "+ score +" /100");
		
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