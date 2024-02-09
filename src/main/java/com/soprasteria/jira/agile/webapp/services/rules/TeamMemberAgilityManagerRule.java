package com.soprasteria.jira.agile.webapp.services.rules;

import com.soprasteria.jira.agile.webapp.models.Issue;
import com.soprasteria.jira.agile.webapp.models.Rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class TeamMemberAgilityManagerRule implements DataAnalysisRule {

    // Thresholds for task assignment
    private static final int MAX_TASKS_THRESHOLD = 5;
    private static final int MIN_TASKS_THRESHOLD = 2;

    @Autowired
    private Rule rule;

    @Override
    public void calculateScore(List<Issue> issues) {
        initializeRuleValues(); // Initialize rule values before calculation
        int balancedUsers = 0;
        for (Issue issue : issues) {
            int assignedTasks = countAssignedTasks(issue.getUser(), issues);
            if (assignedTasks >= MIN_TASKS_THRESHOLD && assignedTasks <= MAX_TASKS_THRESHOLD) {
                balancedUsers++;
            }
        }
        int totalUsers = issues.size();
        double balancedPercentage = (double) balancedUsers / totalUsers;
        int score = (int) (100 - balancedPercentage * 100);
        rule.setScore(score);
    }

    @Override
    public void initializeRuleValues() {
        rule = new Rule();
        rule.setWeight(5); // Définir le poids pour cette règle
        rule.setDescription("Évaluation de l'agilité des membres de l'équipe basée sur l'attribution des tâches");
        rule.setManualAdvice("Considérez une répartition équilibrée de la charge de travail pour les membres de l'équipe ayant trop ou trop peu de tâches assignées.");
    }

    @Override
    public Rule getRule() {
        return rule;
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
}
