package com.soprasteria.jira.agile.webapp.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.soprasteria.jira.agile.webapp.models.Issue;

@Component
public class DataAnalysis{
	
	//private int userPointsScore = 100;
	
	
	/**
	 * Renvoie le liste des tickets avec une complexité supérieure à 23
	 * @param issueList La liste globale des tickets, récupérés depuis Jira ou la bdd
	 * @return la liste contenant tous les tickets trop complexes
	 */
	public ArrayList<Issue> filterBadIssues (ArrayList<Issue> issueList) {
		ArrayList<Issue> badIssues = new ArrayList<>();
		for (int i=0; i < issueList.size(); i++) {
			if (issueList.get(i).getUserPoints() >= 23) {
				badIssues.add(issueList.get(i));
			}
		}
		return badIssues;
	}
	
	public int calculateUserPoints (ArrayList<Issue> badIssueList) 
	{
		int scoreUserPoints = 100;
		for (int i=0; i < badIssueList.size(); i++) {
			if (badIssueList.get(i).getUserPoints() >= 23) {
				if (scoreUserPoints > 0) {
				scoreUserPoints -= 5;
				}
			}
		}
		return scoreUserPoints;
	}
	
	

}