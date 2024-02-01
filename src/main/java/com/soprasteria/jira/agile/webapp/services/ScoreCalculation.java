package com.soprasteria.jira.agile.webapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.soprasteria.jira.agile.webapp.models.Issue;
import com.soprasteria.jira.agile.webapp.models.Rule;
import com.soprasteria.jira.agile.webapp.services.rules.DataAnalysisRule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@ComponentScan(basePackages = "com.soprasteria.jira.agile.webapp.services.rules") 
public class ScoreCalculation {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ScoreCalculation.class);
    
    @Autowired
    private List<DataAnalysisRule> rules;
	
	private List<Rule> listRules;
	
	public void getRules(List<Issue> issues) {
		this.listRules = new ArrayList<>();
		for (DataAnalysisRule rule : rules) {
			rule.calculateScore(issues);
			rule.initializeRuleValues();
			listRules.add(rule.getRule());
		}		
	}
	
	public List<Rule> getListRules(){
		return this.listRules;
	}
	

	public int calculateGlobalScore() {
 		int totalWeight = 0;
		List<Integer> weightedScores = new ArrayList<>();
		int globalScore = 0;
		
		for (Rule rule: listRules) {
			LOGGER.info("Score: " + rule.getScore() + " / Weight: " + rule.getWeight() + " / Description: " + rule.getDescription());
			totalWeight += rule.getWeight();
        	weightedScores.add(rule.getScore() * rule.getWeight());
		}
		for (int i=0; i<weightedScores.size(); i++) {
			globalScore += weightedScores.get(i) / totalWeight;
		}
		
		LOGGER.info("global score : " + globalScore);
		return globalScore;		
	}
}
