<<<<<<< HEAD:src/main/java/com/soprasteria/jira/agile/webapp/services/DataAnalysis.java
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
	
	
=======
//package com.soprasteria.jira.agile.webapp.services;
//import com.soprasteria.jira.agile.webapp.models.Issue_excel;
//import com.soprasteria.jira.agile.webapp.services.dbExcel;
//import java.util.ArrayList;
//
//import org.springframework.stereotype.Component;
//
//public class JiraAnalysis{
//	
//	private int userPointsScore = 100;
//	
//	public void calculateUserPointsScore (ArrayList<Issue_excel> issueList) {
//		for (int i=0; i < issueList.size(); i++) {
//			if (issueList.get(i).getUserPoints() >= 23) {
//				// A TESTER / VOIR SI AUTRE CALCUL PLUS INTERESSANT
//				this.userPointsScore = this.userPointsScore - 100/issueList.size();
//			}
//		}
//	}
//	
//	
>>>>>>> requete-bdd:src/main/java/com/soprasteria/jira/agile/webapp/services/JiraAnalysis.java
	
	
	
	
	
	
	
	
	
}