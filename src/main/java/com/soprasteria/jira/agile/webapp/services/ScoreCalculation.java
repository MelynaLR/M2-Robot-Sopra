package com.soprasteria.jira.agile.webapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.soprasteria.jira.agile.webapp.services.rules.DataAnalysisRule;

@Component
@ComponentScan(basePackages = "com.soprasteria.jira.agile.webapp.services")
public class ScoreCalculation {
	
	@Autowired
    private List<DataAnalysisRule> rules;
	
	public String compScan() {
		for (DataAnalysisRule rule : rules) {
            rule.score();
        }	
		return "TEST IS OK";
	}
}
