package com.soprasteria.jira.agile.webapp.services;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.soprasteria.jira.agile.webapp.models.Issue_excel;

@Component
public class DataAnalysisExcel {
    static List<Issue_excel> issuesList = new ArrayList<>();

    static {
        try {
            issuesList = dbExcel.selectQueryData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void calculateUserPoints(List<Issue_excel> issuesList2) {
        try {
            LocalDate today = LocalDate.now();

            for (int i = 0; i < issuesList2.size(); i++) {
                String sprintEndDateStr = issuesList2.get(i).getSprintEndDate();

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
                    if (!"done".equalsIgnoreCase(issuesList2.get(i).getStatus()) && sprintEndDate.isBefore(today)) {
                        issuesList2.get(i).setUserPoints(issuesList2.get(i).getUserPoints() - 25);
                        if (issuesList2.get(i).getUserPoints() != 100) {
                            // System.out.println(issuesList2.get(i).getUser() + ", " + issuesList2.get(i).getUserPoints() + ", " + issuesList2.get(i).getDescription());
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

    public static Integer getOneLineFromQuery() {
        if (!issuesList.isEmpty()) {
            return getOneLineFromQuery(issuesList.get(0)); // Pass the desired index
        }
        return 0;
    }

    public static Integer getOneLineFromQuery(Issue_excel issue) {
        return issue.getUserPoints();
    }

    public static String getUserNameFromQuery() {
        if (!issuesList.isEmpty()) {
            return getUserNameFromQuery(issuesList.get(0)); // Pass the desired index
        }
        return "";
    }

    public static String getUserNameFromQuery(Issue_excel issue) {
        return issue.getUser();
    }

    public static void main(String[] args) {
        try {
            // Example: Print one line of data from the list
            if (!issuesList.isEmpty()) {
                System.out.println(getOneLineFromQuery(issuesList.get(0))); // Pass the desired index
                System.out.println(getUserNameFromQuery(issuesList.get(0))); // Pass the desired index
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
