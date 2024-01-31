package com.soprasteria.jira.agile.webapp.services;

import com.soprasteria.jira.agile.webapp.models.Issue;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataAnalysisExcel {

    public Integer getOneLineFromQuery(List<Issue> issuesList) {
        if (issuesList != null && !issuesList.isEmpty()) {
            Issue issue = issuesList.get(0);
            return issue.getUserPoints();
        } else {
            return null; 
        }
    }

    public String getUserNameFromQuery(List<Issue> issuesList) {
        if (issuesList != null && !issuesList.isEmpty()) {
            Issue issue = issuesList.get(0);
            return issue.getUser();
        } else {
            return null; 
        }
    }
}
