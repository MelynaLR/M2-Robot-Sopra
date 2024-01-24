package com.soprasteria.jira.agile.webapp.services;

import com.soprasteria.jira.agile.webapp.models.Issue;

import java.util.List;

public class TeamMemberAgilityManager {

    // Thresholds for task assignment
    private static final int MAX_TASKS_THRESHOLD = 5;
    private static final int MIN_TASKS_THRESHOLD = 2;

    public static void evaluateTeamMemberAgility(List<Issue> issues) {
        for (Issue issue : issues) {
            int assignedTasks = countAssignedTasks(issue.getUser(), issues);
            evaluateAgilityScore(issue.getUser(), assignedTasks);
        }
    }

    private static int countAssignedTasks(String assignee, List<Issue> issues) {
        int count = 0;
        for (Issue issue : issues) {
            if (assignee != null && assignee.equals(issue.getUser())) {
                count++;
            }
        }
        return count;
    }

    private static void evaluateAgilityScore(String assignee, int assignedTasks) {
        if (assignedTasks > MAX_TASKS_THRESHOLD) {
            System.out.println("Warning: " + assignee + " has too many assigned tasks. Consider redistributing workload.");
        } else if (assignedTasks < MIN_TASKS_THRESHOLD) {
            System.out.println("Warning: " + assignee + " has too few assigned tasks. Consider assigning more tasks for balance.");
        } else {
            System.out.println(assignee + "'s task assignment is balanced.");
        }
    }
}
