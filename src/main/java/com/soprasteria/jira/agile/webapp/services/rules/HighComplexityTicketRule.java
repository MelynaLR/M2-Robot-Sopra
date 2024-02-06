package com.soprasteria.jira.agile.webapp.services.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.soprasteria.jira.agile.webapp.models.Issue;
import com.soprasteria.jira.agile.webapp.models.Rule;

@Component
@Scope("prototype")
public class HighComplexityTicketRule implements DataAnalysisRule{
	
	@Autowired
	private Rule rule;

	@Override
	public void calculateScore(List<Issue> issues) {
		rule.setScore(0);
		for (Issue issue : issues) {
			if (issue.getUserPoints() >= 21) {
				rule.addIssue(issue);
			}					
		}
		if (issues.size() != 0){
			// returns the percentage of ok issues
			rule.setScore((issues.size() - rule.getIssues().size()) * 100 / issues.size());
		}		
	}

	@Override
	public void initializeRuleValues() {
		rule.setWeight(7);
		rule.setDescription("Tickets avec un nombre de story points trop élevés");
		rule.setManualAdvice("Il est préférable de répartir la charge de travail en plusieurs tickets différents avec un nombre de story points moins élevés.");
	}
	
	@Override
	public Rule getRule() {
		return rule;
	}
}
