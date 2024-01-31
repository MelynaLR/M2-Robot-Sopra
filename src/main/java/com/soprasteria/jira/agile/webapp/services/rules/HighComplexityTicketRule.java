package com.soprasteria.jira.agile.webapp.services.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soprasteria.jira.agile.webapp.models.Issue;
import com.soprasteria.jira.agile.webapp.models.Rule;

@Component
public class HighComplexityTicketRule implements DataAnalysisRule{
	
	@Autowired
	private Rule rule;
	
	List<String> myList = new ArrayList<>();

	@Override
	public void calculateScore(List<Issue> issues) {
		//this.score = 0;
		rule.setScore(0);
		//int problematicIssues = 0;
		for (int i=0; i < issues.size(); i++) {
			if (issues.get(i).getUserPoints() >= 21) {
				rule.addIssue(issues.get(i));
				//problematicIssues += 1;
			}					
		}
		// returns the percentage of ok issues
		rule.setScore((issues.size() - rule.getIssues().size()) * 100 / issues.size());
	}

	@Override
	public void initializeRuleValues() {
		rule.setWeight(2);
		rule.setDescription("ceci est une description");
		rule.setManualAdvice("attention voici conseil");
	}
	

	@Override
	public void setGptAdvice(String gptAdvice) {
		// TODO Auto-generated method stub
	}

	@Override
	public Rule getRule() {
		return rule;
	}
}
