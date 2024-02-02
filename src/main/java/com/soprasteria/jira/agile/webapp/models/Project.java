package com.soprasteria.jira.agile.webapp.models;

import org.springframework.stereotype.Component;

@Component
public class Project {
	private int idProject;
	
	private String nameProject;
	
	public int getIdProject() {
		return this.idProject;
	}
	
	public void setIdProject(int idProject) {
		this.idProject = idProject;
	}
	
	public String getNameProject () {
		return this.nameProject;
	}
	
	public void setNameProject(String nameProject) {
		this.nameProject = nameProject;
	}

}
