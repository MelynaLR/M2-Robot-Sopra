package com.soprasteria.jira.agile.webapp.controller;

import org.springframework.web.bind.annotation.RestController;

import com.soprasteria.jira.agile.webapp.services.JiraAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;


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
	}
	
}