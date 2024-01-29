package com.soprasteria.jira.agile.webapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.soprasteria.jira.agile.webapp.infrastructure.DatabaseReader;
import com.soprasteria.jira.agile.webapp.models.Issue;
import com.soprasteria.jira.agile.webapp.services.rules.DataAnalysisRule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@ComponentScan(basePackages = "com.soprasteria.jira.agile.webapp.services")
public class ScoreCalculation {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ScoreCalculation.class);
	
	@Autowired
    private List<DataAnalysisRule> rules;
	
	private List<Map<Integer, Integer>> listRules;
	
	public void getRules(List<Issue> issues) {
		this.listRules = new ArrayList<>();
		for (DataAnalysisRule rule : rules) {
			rule.calculateScore(issues);
			Map<Integer, Integer> ruleMap = rule.getRuleMap();
			for (Map.Entry<Integer, Integer> entry : ruleMap.entrySet()) {
	            this.listRules.add(ruleMap);
	        }
        }
		
	}
	
	
	
	public int calculateGlobalScore() {
 		int totalWeight = 0;
		List<Integer> weightedScores = new ArrayList<>();
		int globalScore = 0;
		for (Map<Integer, Integer> map : listRules) {
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            	LOGGER.info("Score: " + entry.getKey() + ", Weight: " + entry.getValue());
            	totalWeight += entry.getValue();
            	weightedScores.add(entry.getKey() * entry.getValue());
            }
        }
		for (int i=0; i<weightedScores.size(); i++) {
			globalScore += weightedScores.get(i) / totalWeight;
		}
		System.out.println("global score : " + globalScore);
		return globalScore;		
	}
}
