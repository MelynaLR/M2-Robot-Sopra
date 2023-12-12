package com.soprasteria.jira.agile.webapp.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class MainController{
	@GetMapping(value="/")
	public void retrieveData() {
		System.out.println("coucou yannis");		
	}
	
}