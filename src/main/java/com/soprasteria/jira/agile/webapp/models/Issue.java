package com.soprasteria.jira.agile.webapp.models;




import com.soprasteria.jira.agile.webapp.models.*;


public class Issue{
	private int id;
    private String description;
    private String creationDate;
    private String sprintEndDate;
    private int sprintId;
    private String sprintStartDate;
    private String status;
    private int projectId;
    private String priority;
    private String user;  
    private String taux;
    private Integer userPoints = 0;

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

    public Integer getSprintId() {
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
    public int getProjectId() {
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
    
    public void setSprintId(int sprintId) {
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
    

    public void setProjectId(int projectId) {
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

    
    

	
}