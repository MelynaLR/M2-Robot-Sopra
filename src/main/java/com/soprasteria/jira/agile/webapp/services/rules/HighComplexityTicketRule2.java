package com.soprasteria.jira.agile.webapp.services.rules;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.soprasteria.jira.agile.webapp.models.Issue;

@Component
public class HighComplexityTicketRule2 implements DataAnalysisRule{

	private int score;
	private int weight = 9;

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public int getWeight() {
		return weight;
	}

	@Override
	public void calculateScore(List<Issue> issues) {
		this.score = 0;
		int problematicIssues = 0;
		for (int i=0; i < issues.size(); i++) {
			if (issues.get(i).getUserPoints() >= 23) {
				problematicIssues += 1;
			}					
		}
		// returns the percentage of problematic issues
		this.score = (issues.size() - problematicIssues) * 100 / issues.size() -28;
	}

	@Override
	public Map<Integer, Integer> getRuleMap() {
		Map<Integer, Integer> ruleMap = new HashMap<>();
		ruleMap.put(getScore(), getWeight());
		return ruleMap;
	}
}
