package com.soprasteria.jira.agile.webapp.services.rules;

import java.util.List;

import org.springframework.stereotype.Component;

import com.soprasteria.jira.agile.webapp.models.Issue;

@Component
public class ARuleTest implements DataAnalysisRule{

	
	@Override
	public void score() {
		System.out.println("resultat aruletest");		
	}
	
	@Override
	public void rule(List<Issue> issues) {
		for (int i=0; i < issues.size(); i++) {
			if (issues.get(i).getUserPoints() >= 23) {
				System.out.println("un ticket");
			}						
		}
	}
}
