package com.soprasteria.jira.agile.webapp.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.soprasteria.jira.agile.webapp.models.Issue;

@Component
public class DataAnalysisExcel {

    public static void calculateUserPoints(List<Issue> issueList) {
        try {
            LocalDate today = LocalDate.now();

            for (int i = 0; i < issueList.size(); i++) {
                String sprintEndDateStr = issueList.get(i).getSprintEndDate();

                // Check if the date string is not empty or null before parsing
                if (sprintEndDateStr != null && !sprintEndDateStr.isEmpty()) {
                    // Convert the string to LocalDateTime
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX");
                    LocalDateTime sprintEndDateTime = LocalDateTime.parse(sprintEndDateStr, dateFormatter);

                    // Parse directly to ZonedDateTime if you want to retain time zone information
                    ZonedDateTime zonedDateTime = ZonedDateTime.parse(sprintEndDateStr, dateFormatter);

                    // Extract LocalDate (ignoring time and time zone)
                    LocalDate sprintEndDate = zonedDateTime.toLocalDate();

                    // Compare with today's date
                    if (!"done".equalsIgnoreCase(issueList.get(i).getStatus()) && sprintEndDate.isBefore(today)) {
                    	issueList.get(i).setUserPoints(issueList.get(i).getUserPoints() - 25);
                        if (issueList.get(i).getUserPoints() != 100) {
                        	 System.out.println(issueList.get(i).getUser() + ", " + issueList.get(i).getUserPoints() + ", " + issueList.get(i).getDescription());
                        }
                    }
                   // System.out.println(issuesList2.get(i).getUser() + ", " + issuesList2.get(i).getUserPoints() + ", " + issuesList2.get(i).getDescription());
                } else {
                    // Handle the case when the sprintEndDateStr is empty or null
                	
                   // System.out.println("SprintEndDate is empty or null for issue with ID: " + issuesList2.get(i).getId());
                	
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
