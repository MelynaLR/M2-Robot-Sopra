package com.soprasteria.jira.agile.webapp.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Rule {
	
	private int weight;
	private int score;
	private String description;
	private List<Issue> issues;
	private String gptAdvice;
	private String manualAdvice;
	
	public Rule() {
		issues = new ArrayList<Issue>();
	}
	
	public void refreshIssueList() {
		issues = new ArrayList<Issue>();
	}
	
	public int getWeight() {
		return weight;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<Issue> getIssues() {
		return issues;
	}
	
	public void addIssue(Issue issue) {
		issues.add(issue);
	}
	
	public String getGptAdvice() {
		return gptAdvice;
	}
	
	public void setGptAdvice(String gptAdvice) {
		this.gptAdvice = gptAdvice;
	}
	
	public String getManualAdvice() {
		return manualAdvice;
	}
	
	public void setManualAdvice(String manualAdvice) {
		this.manualAdvice = manualAdvice;
	}
}
