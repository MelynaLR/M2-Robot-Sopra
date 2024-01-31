package com.soprasteria.jira.agile.webapp.services.rules;

import com.soprasteria.jira.agile.webapp.models.Issue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskDistributionAnalysisRule implements DataAnalysisRule {

    private static final int MAX_TASKS_DIFFERENCE_THRESHOLD = 2; // Maximum allowed difference in assigned tasks

    private int score;

    @Override
    public void calculateScore(List<Issue> issues) {
        Map<String, Integer> assignedTasksMap = new HashMap<>();

        // Count assigned tasks for each team member
        for (Issue issue : issues) {
            String assignee = issue.getUser();
            assignedTasksMap.put(assignee, assignedTasksMap.getOrDefault(assignee, 0) + 1);
        }

        // Calculate the difference in assigned tasks among team members
        int maxTasks = assignedTasksMap.values().stream().max(Integer::compareTo).orElse(0);
        int minTasks = assignedTasksMap.values().stream().min(Integer::compareTo).orElse(0);
        int tasksDifference = maxTasks - minTasks;

        // Evaluate the score based on the difference in assigned tasks
        if (tasksDifference > MAX_TASKS_DIFFERENCE_THRESHOLD) {
            System.out.println("Warning: Task distribution imbalance detected. Consider redistributing tasks.");
            score -= 2; // Decrease score for imbalance
        } else {
            System.out.println("Task distribution is balanced.");
            score += 1; // Increase score for balanced distribution
        }
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public int getWeight() {
        return 3; // Example weight, adjust as needed
    }

    @Override
    public Map<Integer, Integer> getRuleMap() {
        Map<Integer, Integer> ruleMap = new HashMap<>();
        ruleMap.put(MAX_TASKS_DIFFERENCE_THRESHOLD, 2); // Example, you can customize as needed
        return ruleMap;
    }
}
