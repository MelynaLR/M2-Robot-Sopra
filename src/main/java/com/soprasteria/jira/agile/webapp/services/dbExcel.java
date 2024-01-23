package com.soprasteria.jira.agile.webapp.services;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.soprasteria.jira.agile.webapp.models.Issue_excel;

public class dbExcel {


// JDBC URL, username, and password of MySQL server
private static final String URL = "jdbc:mysql://localhost:3306/mydb";
private static final String USER = "root";
    
@Value("${bdd.password)")
private static String PASSWORD;

// JDBC variables for opening, closing, and managing connection
private static Connection connection;

public static Connection getConnection() throws SQLException {
    try {
        // Load the JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("JDBC Driver loaded successfully");
        System.out.println(PASSWORD);

        // Establish a connection
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    } catch (ClassNotFoundException e) {
        System.err.println("Error loading JDBC Driver: " + e.getMessage());
        e.printStackTrace();
    }
    return connection;
}


// Other methods for interacting with the database go here...

// Example method to close the connection
public static void closeConnection() throws SQLException {
    if (connection != null && !connection.isClosed()) {
        connection.close();
    }
}



public static void main(String[] args) {
    try {
        // Example usage: establish a connection and do something
        Connection conn = getConnection();
        // ... perform database operations ...
        
        // Example usage: print tables
        //printTables();
        selectQueryData();
        // Close the connection when done
        closeConnection();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

// Method to print tables in the database
public static void printTables() throws SQLException {
    // Retrieve metadata
    DatabaseMetaData metaData = connection.getMetaData();

    // Get the list of tables for the specific database
    ResultSet tables = metaData.getTables("mydb", null, "%", null);

    System.out.println("Tables in the database:");

    // Print each table name
    while (tables.next()) {
        String tableName = tables.getString("TABLE_NAME");
        System.out.println(tableName);
    }
}

public static void selectQueryData() throws SQLException {
    // SQL query
    String selectQuery = "SELECT idIssue, description, creationDate, sprintEndDate, sprintId, sprintStartDate, Status, project_id, priority FROM issue";
    List<Issue_excel> issues = new ArrayList<>();

    try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
         ResultSet resultSet = preparedStatement.executeQuery()) {

        System.out.println("Data in the table:");

        // Print column names
        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
            //System.out.print(resultSet.getMetaData().getColumnName(i) + "\t");
        }
        //System.out.println();

        // Print each row of data
        int i = 1; // Initialize i outside the loop
        while (resultSet.next()) {
            for (int j = 1; j <= resultSet.getMetaData().getColumnCount(); j++) {
                //System.out.print(resultSet.getString(j) + "\t");
            }
            //System.out.println();

            // Create a new Issue_excel object for each row
            Issue_excel issue = new Issue_excel();
            issue.setId(resultSet.getInt("idIssue"));
            issue.setUser("user" + i); // Append i to the user field
            issue.setUserPoints(0); // Append i to the user field
            issue.setDescription(resultSet.getString("description"));
            issue.setCreationDate(resultSet.getString("creationDate"));
            issue.setSprintEndDate(resultSet.getString("sprintEndDate"));
            issue.setSprintId(resultSet.getString("sprintId"));
            issue.setSprintStartDate(resultSet.getString("sprintStartDate"));
            issue.setStatus(resultSet.getString("Status"));
            issue.setProjectId(resultSet.getString("project_id"));
            issue.setPriority(resultSet.getString("priority"));

            // Add the Issue_excel object to the list
            issues.add(issue);

            i++; // Increment i for the next iteration
            System.out.println(issue.getUser() + ", " + issue.getUserPoints() + ", " + issue.getDescription() + ", " + issue.getCreationDate() + ", " + issue.getStatus() + ", " + issue.getPriority());

       }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


}
