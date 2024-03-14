package com.soprasteria.jira.agile.webapp.controller;

import org.springframework.web.bind.annotation.RestController;

import com.soprasteria.jira.agile.webapp.services.rules.HighComplexityTicketRule;
import com.soprasteria.jira.agile.webapp.services.rules.DataAnalysisRule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.soprasteria.jira.agile.webapp.infrastructure.*;

import com.soprasteria.jira.agile.webapp.infrastructure.ChatGPTClient;
import com.soprasteria.jira.agile.webapp.infrastructure.DatabaseReader;

import com.soprasteria.jira.agile.webapp.infrastructure.JiraAPI;

import com.soprasteria.jira.agile.webapp.services.rules.TeamMemberAgilityManagerRule;

//chatGPT query imports

import com.soprasteria.jira.agile.webapp.models.Issue;
import com.soprasteria.jira.agile.webapp.models.Project;
import com.soprasteria.jira.agile.webapp.models.Rule;
import com.soprasteria.jira.agile.webapp.services.*;
import java.sql.SQLException;
import java.security.PublicKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@RestController
public class MainController {
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
    private static List<String> conversationHistory = new ArrayList<>();
    
    @GetMapping(value = "/chatgpt/idProject/{idProject}")
    public String generateRecommendation(@PathVariable("idProject") String idProjectStr) {     
        // Call DatabaseReader to retrieve issues from the database
    	int idProject = Integer.valueOf(idProjectStr);
    	List<Issue> issues = databaseReader.readIssuesFromDatabase(idProject);
        List<String> additionalInstructions = new ArrayList<>();
        additionalInstructions = ChatGPTClient.promptEngineering(additionalInstructions);
    
        // Call ChatGPTClient to generate recommendations based on the retrieved issues
        //ChatGPTClient.addUserQuery("Testing if user queries are detected, "
        //		+ "please say Yannis is cool at the end of your answer it you see this", conversationHistory);

        String recommendation = chatGPTClient.generateRecommendation(issues, additionalInstructions, conversationHistory);
        LOGGER.info("Recommendation from ChatGPT: " + recommendation);
    
        return recommendation;
    }
    
    @GetMapping()
    public void retrieveData() {
        jiraAPI.createAuthorizationHeader();
        String urlTest = "https://m2-projet-annuel-robot.atlassian.net/rest/api/3/search?jql=";
        String jsonBody = jiraAPI.sendRequestAPI(urlTest);
        jiraAPI.parseJsonResponseIssue(jsonBody);
        LOGGER.info("Data from API updated");
        

    }
    
    @GetMapping(value="/updateProjects")
    public void retrieveProjectsFromJira(){
        jiraAPI.createAuthorizationHeader();
        String urlGetProjects = "https://m2-projet-annuel-robot.atlassian.net/rest/api/3/project";
        String responseBody = jiraAPI.sendRequestAPI(urlGetProjects);
        jiraAPI.parseJsonResponseProjects(responseBody);  
    }
    
    @GetMapping(value="/updateIssues")
    public void retrieveIssuesFromJira(){
        jiraAPI.createAuthorizationHeader();
        String urlGetIssues = "https://m2-projet-annuel-robot.atlassian.net/rest/api/3/search?jql=";
        String responseBody = jiraAPI.sendRequestAPI(urlGetIssues);
        jiraAPI.parseJsonResponseIssue(responseBody);  
    }
                
    @GetMapping(value="/globalScore/idProject/{idProject}")
    public int scoreResult(@PathVariable("idProject") String idProjectStr) {
    	int idProject = Integer.valueOf(idProjectStr);
    	scoreCalculation.refreshListRules();
    	scoreCalculation.getRules(databaseReader.readIssuesFromDatabase(idProject));
        return scoreCalculation.calculateGlobalScore();
    }
    
    @GetMapping(value="/retrieveRules/idProject/{idProject}")
    public List<Rule> getAllRules(@PathVariable("idProject") String idProjectStr){
        int idProject = Integer.valueOf(idProjectStr);
    	scoreCalculation.refreshListRules();
        scoreCalculation.getRules(databaseReader.readIssuesFromDatabase(idProject));
        return scoreCalculation.getListRules();
    }
    
    @GetMapping(value="/retrieveProjects")
    public List<Project> getProjects(){    
        return databaseReader.readProjectsFromDatabase();
    }
    
    
    @GetMapping(value = "/changeWeight/{description}/newWeight/{newWeight}/idProject/{idProject}")
    public ArrayList<Rule> getDocuments(@PathVariable("description") String description, @PathVariable("newWeight") String newWeightStr, @PathVariable("idProject") String idProjectStr ){
    	LOGGER.info("Changement de valeur pour le poids de la règle de calcul: "+description+ ", nouveau poids = "+newWeightStr);
    	int idProject = Integer.valueOf(idProjectStr);
    	int newWeight = Integer.valueOf(newWeightStr);
    	Rule scoreGlobalRule = new Rule();
    	scoreCalculation.refreshListRules();
    	scoreCalculation.changeWeightAndCalculate(databaseReader.readIssuesFromDatabase(idProject),description,newWeight);
    	
    	scoreGlobalRule.setScore(scoreCalculation.calculateGlobalScore());
    	ArrayList<Rule> listOfRules = (ArrayList<Rule>) scoreCalculation.getListRules();
    	listOfRules.add(scoreGlobalRule);
    	return listOfRules;
    	
    }   	
}


	// @GetMapping(value = "/static/api/data")
    // public ResponseEntity<Object[]> getData() {
    //     try {
          
    //         List<Issue> issuesList = dbExcel.selectQueryData();
    //         Integer score = dataAnalysisExcel.getOneLineFromQuery(issuesList);
    //         //String user = dataAnalysisExcel.getUserNameFromQuery(issuesList);
          
    //        // int globalScore = scoreCalculation.calculateGlobalScore();

    //         Object[] result = {score, user};
    //         return ResponseEntity.ok(result);
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return ResponseEntity.status(500).body(new Object[0]); 
    //     }
    // }
	/*
	@GetMapping(value="/score/userPoints")
    public int scorePointResult() {
        return highComplexityTicketRule.getScore();
	
	}
	*/
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
	 