package com.soprasteria.jira.agile.webapp.infrastructure;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.soprasteria.jira.agile.webapp.services.ScoreCalculation;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

@Component
@PropertySource("classpath:application.properties")
public class DatabaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseController.class);

    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USER = "root";
    
    private static final String PASSWORD = "KOxNGMYzDuBKmYsNpdxP";
    //private static final String PASSWORD = "!";
    
    // JDBC variables for opening, closing, and managing connection
    private static Connection connection;

    
    public static Connection getConnection() throws SQLException {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            LOGGER.info("JDBC Driver loaded successfully");
            

            
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            LOGGER.error("Error loading JDBC Driver: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }


    // Other methods for interacting with the database go here...

    // Example method to close the connection
    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            LOGGER.info("connection to the database closed");
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