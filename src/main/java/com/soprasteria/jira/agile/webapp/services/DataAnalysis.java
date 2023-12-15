package com.soprasteria.jira.agile.webapp.services;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.soprasteria.jira.agile.webapp.models.Issue;

@Component
public class DataAnalysis{
	
	private int userPointsScore = 100;
	
	public void calculateUserPointsScore (ArrayList<Issue> issueList) {
		for (int i=0; i < issueList.size(); i++) {
			if (issueList.get(i).getUserPoints() >= 23) {
				// A TESTER / VOIR SI AUTRE CALCUL PLUS INTERESSANT
				this.userPointsScore = this.userPointsScore - 100/issueList.size();
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
}