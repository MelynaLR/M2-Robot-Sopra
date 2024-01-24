package com.soprasteria.jira.agile.webapp.builders;

import org.springframework.stereotype.Component;

import com.soprasteria.jira.agile.webapp.models.*;

@Component
public class IssueBuilder{
	private int id;
    private String description;
    private String creationDate;
    private String sprintEndDate;
    private String sprintId;
    private String sprintStartDate;
    private String status;
    private String projectId;
    private String priority;
    private String user;
    private int userPoints;
	
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
    	return this.id;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription () {
    	return this.description;
    }
    
    public void setCreationDate(String creationDate) {
    	this.creationDate = creationDate;
    }
    
    public String getCreationDate() {
    	return this.creationDate;
    }
    
    public void setSprintId(String sprintId) {
    	this.sprintId = sprintId;
    }
    
    public String getSprintId() {
    	return this.sprintId;
    }

    public void setSprintEndDate(String sprintEndDate) {
        this.sprintEndDate = sprintEndDate;
    }
    
    public String getSprintEndDate() {
    	return this.sprintEndDate;
    }

    public void setSprintStartDate(String sprintStartDate) {
        this.sprintStartDate = sprintStartDate;
    }
    
    public String getSprintStartDate() {
    	return this.sprintStartDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatus() {
    	return this.status;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    
    public String getProjectId() {
    	return this.projectId;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
    
    public String getPriority () {
    	return this.priority;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
    public String getUser() {
    	return this.user;
    }

    public void setUserPoints(int userPoints) {
        this.userPoints = userPoints;
    }
    
    public int getUserPoints() {
    	return this.userPoints;
    }
    
    
	public Issue buildIssue() {
		Issue builtIssue = new Issue();
		builtIssue.setId(this.id);
		builtIssue.setDescription(this.description);
		builtIssue.setCreationDate(this.creationDate);
		builtIssue.setSprintEndDate(this.sprintEndDate);
		builtIssue.setSprintStartDate(this.sprintStartDate);
		builtIssue.setStatus(this.status);
		builtIssue.setProjectId(this.projectId);
		builtIssue.setPriority(this.priority);
		builtIssue.setUser(this.user);
		builtIssue.setUserPoints(this.userPoints);
		return builtIssue;	
	}
	
	
	
	
	
	
}