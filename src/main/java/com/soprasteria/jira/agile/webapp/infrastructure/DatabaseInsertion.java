
package com.soprasteria.jira.agile.webapp.infrastructure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.soprasteria.jira.agile.webapp.models.Issue;
import com.soprasteria.jira.agile.webapp.models.Project;

public class DatabaseInsertion {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseInsertion.class);

    public static void insertIssueIntoDatabase(Issue issue) {
	    LOGGER.info("Beginning insertion to the database for issue elements...");
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	
	    try {
	        connection = DatabaseController.getConnection();
	        boolean isIssueExists = checkIssueExists(connection, issue.getId());
	
	        if (isIssueExists) {
	
	            String updateSql = "UPDATE issue SET userName = ?, description = ?, creationDate = ?, " +
	                    "sprintEndDate = ?, SprintID = ?, sprintStartDate = ?, Status = ?, project_id = ?, " +
	                    "priority = ?, userPoints = ? WHERE idIssue = ?";
	
	            preparedStatement = connection.prepareStatement(updateSql);
	            preparedStatement.setString(1, issue.getUser());
	            preparedStatement.setString(2, issue.getDescription());
	            preparedStatement.setString(3, issue.getCreationDate());
	            preparedStatement.setString(4, issue.getSprintEndDate());
	            preparedStatement.setInt(5, issue.getSprintId());
	            preparedStatement.setString(6, issue.getSprintStartDate());
	            preparedStatement.setString(7, issue.getStatus());
	            preparedStatement.setInt(8, issue.getProjectId());
	            preparedStatement.setString(9, issue.getPriority());
	            preparedStatement.setInt(10, issue.getUserPoints());
	            preparedStatement.setInt(11, issue.getId());
	        } else {
	            String insertSql = "INSERT INTO issue (idIssue, userName, description, creationDate, sprintEndDate, SprintID, sprintStartDate, Status, project_id, priority, userPoints) " +
	                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	            preparedStatement = connection.prepareStatement(insertSql);
	            preparedStatement.setInt(1, issue.getId());
	            preparedStatement.setString(2, issue.getUser());
	            preparedStatement.setString(3, issue.getDescription());
	            preparedStatement.setString(4, issue.getCreationDate());
	            preparedStatement.setString(5, issue.getSprintEndDate());
	            preparedStatement.setInt(6, issue.getSprintId());
	            preparedStatement.setString(7, issue.getSprintStartDate());
	            preparedStatement.setString(8, issue.getStatus());
	            preparedStatement.setInt(9, issue.getProjectId());
	            preparedStatement.setString(10, issue.getPriority());
	            preparedStatement.setInt(11, issue.getUserPoints());
	        }
	
	        preparedStatement.executeUpdate();
	
	        LOGGER.info("Issue inserted/updated into the database successfully!");
	    } catch (SQLException e) {
	        LOGGER.error("Error inserting/updating issue into database: {}", e.getMessage());
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
	            LOGGER.error("Error closing resources: {}", e.getMessage());
	            e.printStackTrace();
	        }
	    }
	}
    
    public static void insertProjectIntoDatabase(Project project) {
    	LOGGER.info("Beginning insertion to the database for project elements ...");
    	Connection connection = null;

	    PreparedStatement preparedStatement = null;
	    try {
	    	connection = DatabaseController.getConnection();
	        boolean isProjectExist = checkProjectExist(connection, project.getIdProject());
	    	if (isProjectExist) {
	    		String updateSql = "UPDATE project SET nameProject = ? WHERE idProject = ?";
	            preparedStatement = connection.prepareStatement(updateSql);
	            preparedStatement.setString(1, project.getNameProject());
	            preparedStatement.setInt(2,project.getIdProject());     
	    	}
	    	else {
	    		String insertSQL = "INSERT INTO project (idProject, nameProject) VALUES (?,?)";
	    		preparedStatement = connection.prepareStatement(insertSQL);
	    		preparedStatement.setInt(1,project.getIdProject());
	    		preparedStatement.setString(2,project.getNameProject());
	    		
	    	}
	    	preparedStatement.executeUpdate();	    	
	        LOGGER.info("project inserted/updated into the database successfully!");
	    }
	    catch (SQLException e) {
            LOGGER.error("Error closing resources: {}", e.getMessage());

            e.printStackTrace();
        }
	    finally {
	        try {
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            LOGGER.error("Error closing resources: {}", e.getMessage());
	            e.printStackTrace();
	        }
	    }
    	
    	
    }
    
	
	public static boolean checkIssueExists(Connection connection, int idIssue) throws SQLException {
		
	    String query = "SELECT COUNT(*) FROM issue WHERE idIssue = ?";
	    try (PreparedStatement statement = connection.prepareStatement(query)) {
	        statement.setInt(1, idIssue);
	        try (ResultSet resultSet = statement.executeQuery()) {
	            if (resultSet.next()) {
	                int count = resultSet.getInt(1);
	                return count > 0;
	            }
	        }
	    }
	    return false;
	}
	
public static boolean checkProjectExist(Connection connection, int idProject) throws SQLException {
		
	    String query = "SELECT COUNT(*) FROM project WHERE idProject = ?";
	    try (PreparedStatement statement = connection.prepareStatement(query)) {
	        statement.setInt(1, idProject);
	        try (ResultSet resultSet = statement.executeQuery()) {
	            if (resultSet.next()) {
	                int count = resultSet.getInt(1);
	                return count > 0;
	            }
	        }
	    }
	    return false;
	}

 }
