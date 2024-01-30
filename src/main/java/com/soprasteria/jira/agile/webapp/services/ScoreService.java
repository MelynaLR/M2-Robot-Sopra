package com.soprasteria.jira.agile.webapp.services;

import java.util.List;

import com.soprasteria.jira.agile.webapp.models.Issue;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {
    
    private final ScoreCalculation scoreCalculation;

    public ScoreService(ScoreCalculation scoreCalculation) {
        this.scoreCalculation = scoreCalculation;
    }

    public int getGlobalScore(List<Issue> issuesList) {
        if (issuesList != null && !issuesList.isEmpty()) {
            
            return scoreCalculation.calculateGlobalScore();
        } else {
            return 0; 
        }
    }
}
