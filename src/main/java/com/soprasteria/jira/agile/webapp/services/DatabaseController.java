package com.soprasteria.jira.agile.webapp.services;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;


public class DatabaseController {

    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USER = "root";
    private static final String PASSWORD = "KOxNGMYzDuBKmYsNpdxP";

    // JDBC variables for opening, closing, and managing connection
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC Driver loaded successfully");

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
            printTables();
            
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

}
