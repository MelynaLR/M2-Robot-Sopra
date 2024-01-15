package com.soprasteria.jira.agile.webapp.controller;

import org.springframework.web.bind.annotation.RestController;

import com.soprasteria.jira.agile.webapp.services.JiraAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;


//chatGPT query imports
import com.soprasteria.jira.agile.webapp.models.Issue;
import com.soprasteria.jira.agile.webapp.services.DatabaseReader;
import com.soprasteria.jira.agile.webapp.services.ChatGPTClient;
import java.util.List; // Import List from java.util

@RestController
public class MainController{
	@Autowired
	private JiraAPI jiraAPI;
	
	
	@GetMapping(value="/")
	public void retrieveData() {
		System.out.println("coucou yannis");
		jiraAPI.createAuthorizationHeader();
		String urlTest="https://m2-projet-annuel-robot.atlassian.net/rest/api/3/search?jql=";
		jiraAPI.sendRequestAPI(urlTest);
		
		//testing chatGPT query
		
		// Call DatabaseReader to retrieve issues from the database
        List<Issue> issues = DatabaseReader.readIssuesFromDatabase();

        // Call ChatGPTClient to generate recommendations based on the retrieved issues
        String recommendation = ChatGPTClient.generateRecommendation(issues);

        // Print or use the recommendation as needed
        System.out.println("Recommendation from ChatGPT: " + recommendation);
		
        //System.out.println("Recommendation from chatGPT should have come before this line");
	}
	
}