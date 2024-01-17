package com.soprasteria.jira.agile.webapp.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.soprasteria.jira.agile.webapp.models.Issue;

public class DatabaseReader {

    public static List<Issue> readIssuesFromDatabase() {
        List<Issue> issues = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseController.getConnection();
            String sql = "SELECT issueName, issueComplexity FROM issues";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String issueName = resultSet.getString("issueName");
                int issueComplexity = resultSet.getInt("issueComplexity");

                // Create an Issue object and add it to the list
                Issue issue = new Issue(issueName, issueComplexity);
                issues.add(issue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return issues;
    }
}
