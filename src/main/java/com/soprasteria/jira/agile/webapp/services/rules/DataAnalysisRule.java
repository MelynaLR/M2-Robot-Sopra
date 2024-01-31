package com.soprasteria.jira.agile.webapp.services.rules;

import java.util.List;
import java.util.Map;

import com.soprasteria.jira.agile.webapp.models.Issue;
import com.soprasteria.jira.agile.webapp.models.Rule;

public interface DataAnalysisRule {
	
	void calculateScore(List<Issue> issues);	
	void initializeRuleValues();
    void setGptAdvice(String gptAdvice);
    Rule getRule();
}
