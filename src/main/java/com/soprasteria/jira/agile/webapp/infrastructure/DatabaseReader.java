package com.soprasteria.jira.agile.webapp.infrastructure;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.soprasteria.jira.agile.webapp.models.Issue;

@Component
public class DatabaseReader {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseReader.class);
	
	@Autowired
	private Issue issueBuilder;
	
	
    public List<Issue> readIssuesFromDatabase() {
    	LOGGER.info("Beginning to retrieve issues from the database..");
    	List<Issue> issues = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseController.getConnection();
            String sql = "SELECT * FROM issue";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            LOGGER.info("Going through results of the query ...");
            while (resultSet.next()) {
                
                
            	
                issueBuilder.setUserPoints(resultSet.getInt("userPoints"));
                issueBuilder.setId(resultSet.getInt("idIssue"));
                
                // A MODIFIER POUR AVOIR LES UTILISATEURS
                issueBuilder.setUser(resultSet.getString("userName"));

                issueBuilder.setDescription(resultSet.getString("description"));
                issueBuilder.setCreationDate(resultSet.getString("creationDate"));
                issueBuilder.setSprintEndDate(resultSet.getString("sprintEndDate"));
                issueBuilder.setSprintId(resultSet.getString("sprintId"));
                issueBuilder.setSprintStartDate(resultSet.getString("sprintStartDate"));
                issueBuilder.setStatus(resultSet.getString("Status"));
                issueBuilder.setProjectId(resultSet.getString("project_id"));
                issueBuilder.setPriority(resultSet.getString("priority"));
                

                Issue issue = issueBuilder.buildIssue();
                issues.add(issue);
            }
            LOGGER.info("End of the results");
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
    
    public void printTables() throws SQLException {
        
    	Connection connection = DatabaseController.getConnection();
    	// Retrieve metadata
        DatabaseMetaData metaData = connection.getMetaData();

        // Get the list of tables for the specific database
        ResultSet tables = metaData.getTables("mydb", null, "%", null);

        LOGGER.info("Tables in the database:");

        // Print each table name
        while (tables.next()) {
            String tableName = tables.getString("TABLE_NAME");
            LOGGER.info(tableName);
        }
    }
}
