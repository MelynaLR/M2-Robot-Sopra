package com.soprasteria.jira.agile.webapp.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;

public class DatabaseInsertion {

    public static void insertIssueIntoDatabase(String issueName, int userPoints) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	connection = DatabaseController.getConnection();
            
            //Cette query insère la ligne mais si un issue du même nom existe déjà, elle met à jour ses points de complexité
            String sql = "INSERT INTO issues (issueName, issueComplexity) VALUES (?, ?) ON DUPLICATE KEY UPDATE issueComplexity = ?";
            //String sql = "INSERT INTO issues SET issueName=?, issueComplexity=?";
            //String sql = "INSERT INTO issues (issueName, issueComplexity) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, issueName);
            preparedStatement.setInt(2, userPoints);
            preparedStatement.setInt(3, userPoints);  // Provide the value for ON DUPLICATE KEY UPDATE

            preparedStatement.executeUpdate();

            System.out.println("Issue insérée dans la base de données avec succès!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
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
    }
}
