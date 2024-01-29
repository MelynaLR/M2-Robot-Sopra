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
	
   
    private String taux;
    // Getters
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
    public String getTaux() {
        return taux;
    }
    public Integer getUserPoints() {
        return userPoints;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getSprintEndDate() {
        return sprintEndDate;
    }

    public String getSprintId() {
        return sprintId;
    }

    public String getSprintStartDate() {
        return sprintStartDate;
    }

    public String getStatus() {
        return status;
    }

    public String getUser() {
    	return user;
    }
    public String getProjectId() {
        return projectId;
    }

    public String getPriority() {
        return priority;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }
    

    public void setDescription(String description) {
        this.description = description;
    }
    
   
    public void setTaux(String taux) {
        this.taux = taux;
    }
    public void setUserPoints(Integer userPoints) {
        this.userPoints = userPoints;
    }
    
    public void setCreationDate(String creationDate) {
    	this.creationDate = creationDate;
    }
    

    
    public void setSprintId(String sprintId) {
    	this.sprintId = sprintId;
    }
    

    public void setSprintEndDate(String sprintEndDate) {
        this.sprintEndDate = sprintEndDate;
    }
    

    public void setSprintStartDate(String sprintStartDate) {
        this.sprintStartDate = sprintStartDate;
    }
    

    public void setStatus(String status) {
        this.status = status;
    }
    

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    

    public void setPriority(String priority) {
        this.priority = priority;
    }
    

    public void setUser(String user) {
        this.user = user;
    }
    
    public void setUserPoints(int userPoints) {
        this.userPoints = userPoints;
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