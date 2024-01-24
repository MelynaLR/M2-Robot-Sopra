package com.soprasteria.jira.agile.webapp.services.rules;

import java.util.List;

import com.soprasteria.jira.agile.webapp.models.Issue;

public interface DataAnalysisRule {

	void test();	
	
	float getWeight();
	
	void getScore(List<Issue> issues);
}
