package com.soprasteria.jira.agile.webapp.infrastructure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.soprasteria.jira.agile.webapp.models.Issue;
import com.soprasteria.jira.agile.webapp.services.ScoreCalculation;

public class DatabaseInsertion {
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseInsertion.class);

    public static void insertIssueIntoDatabase(Issue issue) {
        LOGGER.info("Beginning insertion to the database ...");
    	Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	connection = DatabaseController.getConnection();
            
            //Cette query insère la ligne mais si un issue du même nom existe déjà, elle met à jour ses points de complexité
            //String sql = "INSERT INTO issues (issueName, issueComplexity) VALUES (?, ?) ON DUPLICATE KEY UPDATE issueComplexity = ?";
            String sql = "INSERT INTO issue (userName, description, creationDate, sprintEndDate, sprintId, sprintStartDate, Status, project_id, priority, userPoints) VALUES (?,?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE userPoints = ?";
            //String sql = "INSERT INTO issues SET issueName=?, issueComplexity=?";
            //String sql = "INSERT INTO issues (issueName, issueComplexity) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, issue.getUser());
            preparedStatement.setString(2, issue.getDescription());
            preparedStatement.setString(3, issue.getCreationDate());
            preparedStatement.setString(4, issue.getSprintEndDate());
            preparedStatement.setString(5, issue.getSprintId());
            preparedStatement.setString(6, issue.getSprintStartDate());
            preparedStatement.setString(7, issue.getStatus());
            preparedStatement.setString(8, issue.getProjectId());
            preparedStatement.setString(9, issue.getPriority());
            preparedStatement.setInt(10, issue.getUserPoints());
            preparedStatement.setInt(11, issue.getUserPoints());

            preparedStatement.executeUpdate();

            LOGGER.info("Issues inserted in the database!");
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
