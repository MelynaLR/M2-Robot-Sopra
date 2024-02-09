package com.soprasteria.jira.agile.webapp.services.rules;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.soprasteria.jira.agile.webapp.models.Issue;
import com.soprasteria.jira.agile.webapp.models.Rule;

@Component
@Scope("prototype")
public class UnresolvedTicketRule implements DataAnalysisRule{

	@Autowired
	private Rule rule;

	@Override
	public void calculateScore(List<Issue> issues) {
		//this.rule = new Rule();
		rule.setScore(0);
		LocalDate today = LocalDate.now();
		
		for (int i = 0; i < issues.size(); i++) {
            String sprintEndDateStr = issues.get(i).getSprintEndDate();

            // Check if the date string is not empty or null before parsing
            if (sprintEndDateStr != null && !sprintEndDateStr.isEmpty()) {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
				LocalDateTime sprintEndDateTime = LocalDateTime.parse(sprintEndDateStr, dateFormatter);

                ZonedDateTime zonedDateTime = ZonedDateTime.parse(sprintEndDateStr, dateFormatter);
                LocalDate sprintEndDate = zonedDateTime.toLocalDate();

               
                if (!"done".equalsIgnoreCase(issues.get(i).getStatus()) && sprintEndDate.isBefore(today)) {
                	rule.addIssue(issues.get(i));
//                	issues.get(i).setUserPoints(issues.get(i).getUserPoints() - 25);
//                    if (issues.get(i).getUserPoints() != 100) {
//                        System.out.println(issues.get(i).getUser() + ", " + issues.get(i).getUserPoints() + ", " + issues.get(i).getDescription());
//                    }
                } else {
                    System.out.println("SprintEndDate is empty or null for issue with ID: " + issues.get(i).getId());
                }
            }
		}
		if (issues.size() != 0){
			// returns the percentage of ok issues
			rule.setScore((issues.size() - rule.getIssues().size()) * 100 / issues.size());
		}
	}

	@Override
	public void initializeRuleValues() {
		rule.setWeight(2);
		rule.setDescription("Ticket non résolu à la fin du sprint");
		rule.setManualAdvice("Identifiez les raisons pour lesquelles le ticket n'a pas été complété. Cela pourrait être dû à des problèmes techniques, des lacunes dans la compréhension des exigences, des dépendances non résolues, ou d'autres facteurs.");
	}

	@Override
	public Rule getRule() {
		return rule;
	}
}
