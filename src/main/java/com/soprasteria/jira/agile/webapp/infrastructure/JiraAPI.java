package com.soprasteria.jira.agile.webapp.infrastructure;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.soprasteria.jira.agile.webapp.infrastructure.DatabaseController;
import com.soprasteria.jira.agile.webapp.infrastructure.DatabaseInsertion;
import com.soprasteria.jira.agile.webapp.models.Issue;
import com.soprasteria.jira.agile.webapp.models.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;




@Component
public class JiraAPI {

	@Value("${jira.user}")
    private String username;
	
	@Value("${jira.token}")
    private String tokenApi;
	
    private String headerType = "application/json";
    private String headerAuthorization = "Authorization";
    private String headerValue;
    private ArrayList<Issue> issueList;
    
    public ArrayList<Issue> getIssueList(){
    	return this.issueList;
    }

    public void createAuthorizationHeader() {
        String value = this.username + ":" + this.tokenApi;
        this.headerValue = "Basic " + Base64.getEncoder().encodeToString(value.getBytes());
    }

    public String sendRequestAPI(String urlAPI) {
        Client client = Client.create();

        WebResource webResource = client.resource(urlAPI);

        ClientResponse response = webResource
                .header(headerAuthorization, this.headerValue)
                .type(this.headerType)
                .accept(this.headerType)
                .get(ClientResponse.class);

        if (response.getStatus() == 200) {
            String responseBody = response.getEntity(String.class);
            System.out.println("Issues and their complexity points incoming : ");
            return responseBody;
        } else {
            System.err.println("Erreur lors de la requête : " + response.getStatus());
            return "error";
        }
    }

    public void parseJsonResponseIssue(String responseBody) {
    	issueList = new ArrayList<>();
    	JSONObject json = new JSONObject(responseBody);

        
        JSONArray issuesArray = json.getJSONArray("issues");

        for (int i = 0; i < issuesArray.length(); i++) {
        	Issue newIssue = new Issue();
        	JSONObject issue = issuesArray.getJSONObject(i);
        	
        	if (issue.has("id")) {
        		newIssue.setId(issue.getInt("id"));
        		System.out.println("Issue id: "+newIssue.getId());
        	}
        	
        	if (issue.getJSONObject("fields").has("summary")) {
                newIssue.setDescription(issue.getJSONObject("fields").getString("summary"));
               
            }
        	if (issue.getJSONObject("fields").has("customfield_10032")) {
        		
        		newIssue.setUserPoints(issue.getJSONObject("fields").optInt("customfield_10032",0));
            } 
        	if (issue.getJSONObject("fields").has("assignee")&& issue.getJSONObject("fields").get("assignee") instanceof JSONObject) {
        		
        		
        		newIssue.setUser(issue.getJSONObject("fields").getJSONObject("assignee").getString("displayName"));
            } 
        	if (issue.getJSONObject("fields").has("created") && issue.getJSONObject("fields").get("created")!= null && issue.getJSONObject("fields").get("created") instanceof String) {
        		newIssue.setCreationDate(issue.getJSONObject("fields").getString("created"));
            } 
       	
            if (issue.getJSONObject("fields").has("customfield_10020") &&
            issue.getJSONObject("fields").get("customfield_10020") instanceof JSONArray) {
            JSONArray customfield_10020Array = issue.getJSONObject("fields").getJSONArray("customfield_10020");
            
            if (customfield_10020Array.length() > 0 && customfield_10020Array.getJSONObject(0).has("id")) {
                newIssue.setSprintId(Integer.valueOf(customfield_10020Array.getJSONObject(0).getInt("id")));

            } else {
                newIssue.setSprintId(-1); // Set Sprint ID to 0 if not present or invalid
            }
            }
        	
            if (issue.getJSONObject("fields").has("customfield_10020") &&
            issue.getJSONObject("fields").get("customfield_10020") instanceof JSONArray) {
            JSONArray customfield_10020Array = issue.getJSONObject("fields").getJSONArray("customfield_10020");
                  
            if (customfield_10020Array.length() > 0 && customfield_10020Array.getJSONObject(0).has("startDate")) {
            	newIssue.setSprintStartDate(String.valueOf(customfield_10020Array.getJSONObject(0).getString("startDate")));   		            
            }		
            }
            
            if (issue.getJSONObject("fields").has("customfield_10020") &&
                issue.getJSONObject("fields").get("customfield_10020") instanceof JSONArray) {
                JSONArray customfield_10020Array = issue.getJSONObject("fields").getJSONArray("customfield_10020");
                    
                if (customfield_10020Array.length() > 0 && customfield_10020Array.getJSONObject(0).has("endDate")) {
                    newIssue.setSprintEndDate(String.valueOf(customfield_10020Array.getJSONObject(0).getString("endDate")));   		            
                }		
            }
            
            if (issue.getJSONObject("fields").has("status")) {
                JSONObject statusObject = issue.getJSONObject("fields").getJSONObject("status");
                if (statusObject.has("statusCategory")) {
                    JSONObject statusCategoryObject = statusObject.getJSONObject("statusCategory");
                    if (statusCategoryObject.has("name")) {
                        String statusCategoryName = statusCategoryObject.getString("name");
                        newIssue.setStatus(statusCategoryName);
                    }
                }
            }
        	if (issue.getJSONObject("fields").has("project") && issue.getJSONObject("fields").get("project")!= null && issue.getJSONObject("fields").get("project") instanceof String) {
        		newIssue.setProjectId(issue.getJSONObject("fields").getString("project"));
            } 
        	if (issue.getJSONObject("fields").has("priority") && issue.getJSONObject("fields").get("priority")!= null && issue.getJSONObject("fields").get("priority") instanceof String) {
        		newIssue.setPriority(issue.getJSONObject("fields").getString("priority") );
            }
            
            issueList.add(newIssue);
            
            try {
                DatabaseInsertion.insertIssueIntoDatabase(newIssue);
                System.out.println("Issue inserted into databases, details : " + newIssue);
            } catch (Exception e) {
                e.printStackTrace(); 
            }
        }
    }
    
    public List<Project> parseJsonResponseProjects(String responseBody) {
    	ArrayList<Project> projectList = new ArrayList<>(); 
    	JSONArray projectArray = new JSONArray(responseBody);
         for (int i = 0; i < projectArray.length(); i++) {
        	 Project newProject = new Project();
             JSONObject project = projectArray.getJSONObject(i);

             if (project.has("id")) {
         		newProject.setIdProject(project.getInt("id"));
         		System.out.println("Issue id: "+newProject.getIdProject());
         	}         	
         	if (project.has("name")) {
                 newProject.setNameProject(project.getString("name"));
                
             }
            projectList.add(newProject);
            System.out.println("Project retrieved, details : " + newProject);
            

        }
        return projectList;
    }
    
    
    

}