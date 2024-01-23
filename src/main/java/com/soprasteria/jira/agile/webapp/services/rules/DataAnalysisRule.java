package com.soprasteria.jira.agile.webapp.services.rules;

import java.util.List;

import com.soprasteria.jira.agile.webapp.models.Issue;

public interface DataAnalysisRule {

	void score();
	
	void rule(List<Issue> issues);
}
