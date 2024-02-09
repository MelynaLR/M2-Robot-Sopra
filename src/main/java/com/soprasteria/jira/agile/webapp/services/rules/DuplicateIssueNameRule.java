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
public class DuplicateIssueNameRule implements DataAnalysisRule {

	@Autowired
    private Rule rule;

    @Override
    public void calculateScore(List<Issue> issues) {
        initializeRuleValues(); // Initialize rule values before calculation
        Map<String, Integer> issueNameCount = new HashMap<>();

        // Count occurrences of each issue name
        for (Issue issue : issues) {
            String issueName = issue.getDescription();
            issueNameCount.put(issueName, issueNameCount.getOrDefault(issueName, 0) + 1);
        }

        // Calculate the proportion of duplicate names
        int totalIssues = issues.size();
        int duplicateCount = 0;
        for (int count : issueNameCount.values()) {
            if (count > 1) {
                duplicateCount += count - 1; // Subtract 1 to count only duplicates
            }
        }

        // Calculate the score based on the proportion of duplicate names
        double duplicateProportion = (double) duplicateCount / totalIssues;
        int score = (int) (100 - duplicateProportion * 100); // Inverse proportion for the score

        rule.setScore(score);
    }

    @Override
    public void initializeRuleValues() {
        rule.setWeight(3); // Set the weight for this rule
        rule.setDescription("Évaluation de la présence de noms d'issues en double");
        rule.setManualAdvice("Évitez d'avoir plusieurs issues avec le même nom, cela peut entraîner de la confusion dans le suivi des tâches.");
    }

    @Override
    public Rule getRule() {
        return rule;
    }
}
