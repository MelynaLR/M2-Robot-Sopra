package com.soprasteria.jira.agile.webapp.controller;

import org.springframework.web.bind.annotation.RestController;

import com.soprasteria.jira.agile.webapp.services.JiraAPI;
import com.soprasteria.jira.agile.webapp.services.ScoreCalculation;
import com.soprasteria.jira.agile.webapp.services.rules.HighComplexityTicketRule;
import com.soprasteria.jira.agile.webapp.services.rules.DataAnalysisRule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//chatGPT query imports
import com.soprasteria.jira.agile.webapp.models.Issue;
import com.soprasteria.jira.agile.webapp.services.DatabaseReader;
import com.soprasteria.jira.agile.webapp.services.ChatGPTClient;
import com.soprasteria.jira.agile.webapp.services.DataAnalysis;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List; // Import List from java.util


@RestController()
public class MainController{
	@Autowired
	private JiraAPI jiraAPI;
	
	@Autowired
	private DataAnalysis dataAnalysis;
	
	@Autowired
	private ChatGPTClient chatGPTClient;
	
	@Autowired 
	private ScoreCalculation scoreCalculation;
	
	
	@GetMapping()
	public void retrieveData() {
		jiraAPI.createAuthorizationHeader();
		String urlTest="https://m2-projet-annuel-robot.atlassian.net/rest/api/3/search?jql=";
		jiraAPI.sendRequestAPI(urlTest);
		
		//testing chatGPT query
		
		// Call DatabaseReader to retrieve issues from the database
        List<Issue> issues = DatabaseReader.readIssuesFromDatabase();

        // Call ChatGPTClient to generate recommendations based on the retrieved issues
        String recommendation = chatGPTClient.generateRecommendation(issues);

        // Print or use the recommendation as needed
        System.out.println("Recommendation from ChatGPT: " + recommendation);        
	}
	
	@GetMapping(value="/gpt/recommandations")
	public void gptRecommandations() {
		//jiraAPI.createAuthorizationHeader();
		 List<Issue> issues = DatabaseReader.readIssuesFromDatabase();
        
		 // Call ChatGPTClient to generate recommendations based on the retrieved issues	       
		 String recommendation = chatGPTClient.generateRecommendation(issues);
	        
		 // Print or use the recommendation as needed	        
		 System.out.println("Recommendation from ChatGPT: " + recommendation);
	}
	
	@GetMapping(value="/score/userPoints")
	public void scoreUserPoints() {
		ArrayList<Issue> issues = (ArrayList<Issue>) DatabaseReader.readIssuesFromDatabase();
		ArrayList<Issue> badIssues = dataAnalysis.filterBadIssues(issues);
		int score = dataAnalysis.calculateUserPoints(badIssues);
		System.out.println("Score de user points: "+ score +" /100");		
	}
				
	@GetMapping(value="/comp")
	public void scoreResult() {
		scoreCalculation.compScan();
	}
}