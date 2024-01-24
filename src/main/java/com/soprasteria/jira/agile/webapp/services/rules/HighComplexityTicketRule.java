package com.soprasteria.jira.agile.webapp.services.rules;

import java.util.List;

import org.springframework.stereotype.Component;

import com.soprasteria.jira.agile.webapp.models.Issue;

@Component
public class HighComplexityTicketRule implements DataAnalysisRule{

	private float weight;

	@Override
	public void test() {
		System.out.println("highcomplexityticket");		
	}

	@Override
	public float getWeight() {
		return weight;
	}

	@Override
	public void getScore(List<Issue> issues) {
		for (int i=0; i < issues.size(); i++) {
			if (issues.get(i).getUserPoints() >= 23) {
				System.out.println("un ticket");
			}						
		}		
	}
}
