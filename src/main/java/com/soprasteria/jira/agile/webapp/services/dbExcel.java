package com.soprasteria.jira.agile.webapp.services;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.soprasteria.jira.agile.webapp.models.Issue;

public class dbExcel {



private static final String URL = "jdbc:mysql://localhost:3306/mydb";
private static final String USER = "root";
private static final String PASSWORD = "root";
private static Connection connection;


    public static Connection getConnection() throws SQLException {
        try {
        
            Class.forName("com.mysql.cj.jdbc.Driver");
          
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Error loading JDBC Driver: " + e.getMessage());
            e.printStackTrace();
            throw new SQLException("JDBC Driver not found", e);
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static List<Issue> selectQueryData() throws SQLException {
        List<Issue> issues = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT idIssue, description, creationDate, sprintEndDate, sprintId, sprintStartDate, Status, project_id, priority FROM issue");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Issue issue = new Issue();
                issue.setId(resultSet.getInt("idIssue"));
                issue.setUser("user" + resultSet.getInt("idIssue"));
                issue.setUserPoints(100);
                issue.setDescription(resultSet.getString("description"));
                issue.setCreationDate(resultSet.getString("creationDate"));
                issue.setSprintEndDate(resultSet.getString("sprintEndDate"));
                issue.setSprintId(resultSet.getString("sprintId"));
                issue.setSprintStartDate(resultSet.getString("sprintStartDate"));
                issue.setStatus(resultSet.getString("Status"));
                issue.setProjectId(resultSet.getString("project_id"));
                issue.setPriority(resultSet.getString("priority"));

                issues.add(issue);
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            closeConnection();
        }

        return issues;
    }
}
