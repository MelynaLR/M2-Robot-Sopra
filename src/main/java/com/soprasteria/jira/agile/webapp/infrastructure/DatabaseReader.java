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
import com.soprasteria.jira.agile.webapp.models.Project;

@Component
public class DatabaseReader {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseReader.class);
	
	
	
    public List<Issue> readIssuesFromDatabase(int idProject) {
    	LOGGER.info("Beginning to retrieve issues from the database..");
    	List<Issue> issues = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseController.getConnection();
            boolean doExist = checkProjectExist(connection,idProject);
            String sql = "";
            if (doExist) {
            	sql = "SELECT * FROM issue WHERE project_id = ?";
            	preparedStatement = connection.prepareStatement(sql);
            	preparedStatement.setInt(1,idProject);
            	
            }
            else {
            	sql = "SELECT * FROM issue";    
            	preparedStatement = connection.prepareStatement(sql);
            }            
            
            resultSet = preparedStatement.executeQuery();
            LOGGER.info("Going through results of the query ...");
            while (resultSet.next()) {
                Issue issue = new Issue();
                
                
            	if (resultSet.getString("userName") != null) {
            		issue.setUser(resultSet.getString("userName"));
            	}
                issue.setUserPoints(resultSet.getInt("userPoints"));
                
                issue.setId(resultSet.getInt("idIssue"));
                
                // A MODIFIER POUR AVOIR LES UTILISATEURS
                

                issue.setDescription(resultSet.getString("description"));
                issue.setCreationDate(resultSet.getString("creationDate"));
                issue.setSprintEndDate(resultSet.getString("sprintEndDate"));
                issue.setSprintId(resultSet.getInt("sprintId"));
                issue.setSprintStartDate(resultSet.getString("sprintStartDate"));
                issue.setStatus(resultSet.getString("Status"));
                issue.setProjectId(resultSet.getInt("project_id"));
                issue.setPriority(resultSet.getString("priority"));
                

                
                issues.add(issue);
                LOGGER.info("Issue retrieved: "+issue.getDescription());
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
    
    public List<Project> readProjectsFromDatabase() {
    	LOGGER.info("Beginning to retrieve projects from the database..");
    	List<Project> projects = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseController.getConnection();
            String sql = "SELECT * FROM project";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            LOGGER.info("Going through results of the query ...");
            while (resultSet.next()) {
                Project project = new Project();
                
                
            	if (resultSet.getString("nameProject") != null) {
            		project.setNameProject(resultSet.getString("nameProject"));
            	}
                project.setIdProject(resultSet.getInt("idProject"));            
                projects.add(project);
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

        return projects;
    }
    
    public List<Issue> retrieveIssuesFromAProject(int idProject){
    	
    	List<Issue> issues = new ArrayList<>();
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseController.getConnection();
            boolean doExist = checkProjectExist(connection,idProject);
            if (doExist)
            {
            	
            }
            LOGGER.info("Beginning to retrieve issues from the database for the project with the id "+ idProject);
        }catch (SQLException e) {
        e.printStackTrace();
	    } 
        finally {	        
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
