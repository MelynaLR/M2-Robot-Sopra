package com.soprasteria.jira.agile.webapp.services;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseController {

    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/jiraAPI";
    private static final String USER = "root";
    private static final String PASSWORD = "KOxNGMYzDuBKmYsNpdxP";

    // JDBC variables for opening, closing, and managing connection
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
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

            // Close the connection when done
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
