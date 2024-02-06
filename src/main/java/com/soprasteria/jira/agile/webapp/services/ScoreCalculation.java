package com.soprasteria.jira.agile.webapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.soprasteria.jira.agile.webapp.models.Issue;
import com.soprasteria.jira.agile.webapp.models.Rule;
import com.soprasteria.jira.agile.webapp.services.rules.DataAnalysisRule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Scope("prototype")
@ComponentScan(basePackages = "com.soprasteria.jira.agile.webapp.services.rules") 
public class ScoreCalculation {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ScoreCalculation.class);
    
    @Autowired
    private List<DataAnalysisRule> rules;
	
	private List<Rule> listRules;
	
	public void refreshListRules() {
		this.listRules = new ArrayList<>();
	}
	
	public void getRules(List<Issue> issues) {
		this.listRules = new ArrayList<>();
		for (DataAnalysisRule rule : rules) {
			rule.getRule().refreshIssueList();
			rule.calculateScore(issues);
			rule.initializeRuleValues();
			listRules.add(rule.getRule());
		}		
	}
	
	public List<Rule> getListRules(){
		return this.listRules;
	}
	
	/**
	 * Refresh the list, change the weight of a rule and calculate the new scores
	 * @param the list of issues from the project or the Jira space
	 * @param description The description of the rule which the weight will be changed
	 * @param newWeight the new weight of the rule
	 */
	public void changeWeightAndCalculate (List<Issue> issues, String description, int newWeight) {
		this.listRules = new ArrayList<>();
		for (DataAnalysisRule rule : rules) {
			if (rule.getRule().getDescription().equals(description)){
				rule.getRule().setWeight(newWeight);
			}
			rule.calculateScore(issues);
			rule.initializeRuleValues();
			listRules.add(rule.getRule());
		}
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
