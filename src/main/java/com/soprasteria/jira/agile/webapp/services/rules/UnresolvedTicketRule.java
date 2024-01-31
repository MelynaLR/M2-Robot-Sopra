//package com.soprasteria.jira.agile.webapp.services.rules;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.ZonedDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.stereotype.Component;
//
//import com.soprasteria.jira.agile.webapp.models.Issue;
//
//@Component
//public class UnresolvedTicketRule implements DataAnalysisRule{
//
//	private int score;
//	private int weight = 6;
//
//	@Override
//	public int getScore() {
//		return score;
//	}
//
//	@Override
//	public int getWeight() {
//		return weight;
//	}
//
//	@Override
//	public void calculateScore(List<Issue> issues) {
//		this.score = 0;
//		int problematicIssues = 0;
//		LocalDate today = LocalDate.now();
//		
//		for (int i = 0; i < issues.size(); i++) {
//            String sprintEndDateStr = issues.get(i).getSprintEndDate();
//
//            // Check if the date string is not empty or null before parsing
//            if (sprintEndDateStr != null && !sprintEndDateStr.isEmpty()) {
//                // Convert the string to LocalDateTime
//                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX");
//                LocalDateTime sprintEndDateTime = LocalDateTime.parse(sprintEndDateStr, dateFormatter);
//
//                // Parse directly to ZonedDateTime if you want to retain time zone information
//                ZonedDateTime zonedDateTime = ZonedDateTime.parse(sprintEndDateStr, dateFormatter);
//
//                // Extract LocalDate (ignoring time and time zone)
//                LocalDate sprintEndDate = zonedDateTime.toLocalDate();
//
//                // Compare with today's date
//                if (!"done".equalsIgnoreCase(issues.get(i).getStatus()) && sprintEndDate.isBefore(today)) {
//                	problematicIssues += 1;
////                	issues.get(i).setUserPoints(issues.get(i).getUserPoints() - 25);
////                    if (issues.get(i).getUserPoints() != 100) {
////                        System.out.println(issues.get(i).getUser() + ", " + issues.get(i).getUserPoints() + ", " + issues.get(i).getDescription());
////                    }
//                } else {
//                    // Handle the case when the sprintEndDateStr is empty or null
//                    System.out.println("SprintEndDate is empty or null for issue with ID: " + issues.get(i).getId());
//                }
//            }
//		}
//		// returns the percentage of ok issues
//		System.out.println("prob issues : " + problematicIssues);
//		this.score = (issues.size() - problematicIssues) * 100 / issues.size();
//	}
//
//	@Override
//	public Map<Integer, Integer> getRuleMap() {
//		Map<Integer, Integer> ruleMap = new HashMap<>();
//		ruleMap.put(getScore(), getWeight());
//		return ruleMap;
//	}
//}
