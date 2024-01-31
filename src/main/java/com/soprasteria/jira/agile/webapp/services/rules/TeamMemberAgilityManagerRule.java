package com.soprasteria.jira.agile.webapp.services.rules;

import com.soprasteria.jira.agile.webapp.models.Issue;
import com.soprasteria.jira.agile.webapp.models.Rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamMemberAgilityManagerRule implements DataAnalysisRule {

    // Thresholds for task assignment
    private static final int MAX_TASKS_THRESHOLD = 5;
    private static final int MIN_TASKS_THRESHOLD = 2;

    private int score;

    @Override
    public void calculateScore(List<Issue> issues) {
        for (Issue issue : issues) {
            int assignedTasks = countAssignedTasks(issue.getUser(), issues);
            evaluateAgilityScore(issue.getUser(), assignedTasks);
        }
    }

    private int countAssignedTasks(String assignee, List<Issue> issues) {
        int count = 0;
        for (Issue issue : issues) {
            if (assignee != null && assignee.equals(issue.getUser())) {
                count++;
            }
        }
        return count;
    }

    private void evaluateAgilityScore(String assignee, int assignedTasks) {
        if (assignedTasks > MAX_TASKS_THRESHOLD) {
            System.out.println("Warning: " + assignee + " has too many assigned tasks. Consider redistributing workload.");
            score -= 2; // Decrease score for too many tasks
        } else if (assignedTasks < MIN_TASKS_THRESHOLD) {
            System.out.println("Warning: " + assignee + " has too few assigned tasks. Consider assigning more tasks for balance.");
            score -= 1; // Decrease score for too few tasks
        } else {
            System.out.println(assignee + "'s task assignment is balanced.");
            score += 1; // Increase score for balanced tasks
        }
    }


	@Override
	public void initializeRuleValues() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setGptAdvice(String gptAdvice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rule getRule() {
		// TODO Auto-generated method stub
		return null;
	}
}