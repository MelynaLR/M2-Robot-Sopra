package com.soprasteria.jira.agile.webapp.services;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

@Component
@PropertySource("classpath:application.properties")
public class DatabaseController {

    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USER = "root";
    
    private static String PASSWORD = "root";
    //private static final String PASSWORD = "root";
    
    @Value("${bdd.password}")
    private String recupPassword = "test";
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