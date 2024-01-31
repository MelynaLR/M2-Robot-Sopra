package com.soprasteria.jira.agile.webapp.infrastructure;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Base64;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.soprasteria.jira.agile.webapp.models.Issue;

//added
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

    public void sendRequestAPI(String urlAPI) {
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
            parseJsonResponse(responseBody);
        } else {
            System.err.println("Erreur lors de la requête : " + response.getStatus());
        }
    }

    private void parseJsonResponse(String responseBody) {
    	issueList = new ArrayList<>();
    	JSONObject json = new JSONObject(responseBody);

        
        JSONArray issuesArray = json.getJSONArray("issues");

        for (int i = 0; i < issuesArray.length(); i++) {
        	Issue newIssue = new Issue();
        	JSONObject issue = issuesArray.getJSONObject(i);
        	
        	
        	
        	if (issue.getJSONObject("fields").has("summary")) {
                newIssue.setDescription(issue.getJSONObject("fields").getString("summary"));
                System.out.println("Issue name: "+newIssue.getDescription());
            }
        	if (issue.getJSONObject("fields").has("customfield_10032")) {
        		
        		newIssue.setUserPoints(issue.getJSONObject("fields").optInt("customfield_10032", -1));
            } 
        	if (issue.getJSONObject("fields").has("assignee")&& issue.getJSONObject("fields").get("assignee") instanceof JSONObject) {
        		
        		System.out.println(issue.getJSONObject("fields").getJSONObject("assignee").getString("displayName"));
        		newIssue.setUser(issue.getJSONObject("fields").getJSONObject("assignee").getString("displayName"));
            } 
        	if (issue.getJSONObject("fields").has("created") && issue.getJSONObject("fields").get("created")!= null && issue.getJSONObject("fields").get("created") instanceof String) {
        		newIssue.setCreationDate(issue.getJSONObject("fields").getString("created"));
            } 
       	
            if (issue.getJSONObject("fields").has("customfield_10020") &&
        		    issue.getJSONObject("fields").get("customfield_10020") instanceof JSONArray) {
        		    JSONArray customfield_10020Array = issue.getJSONObject("fields").getJSONArray("customfield_10020");
      		    
        		    if (customfield_10020Array.length() > 0 && customfield_10020Array.getJSONObject(0).has("id")) {
        		    	newIssue.setSprintId(String.valueOf(customfield_10020Array.getJSONObject(0).getInt("id")));
                        System.out.println("SprintID: "+newIssue.getSprintId());   		            
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
            

        	if (issue.getJSONObject("fields").has("status") && issue.getJSONObject("fields").get("status")!= null && issue.getJSONObject("fields").get("status") instanceof String) {
        		newIssue.setStatus(issue.getJSONObject("fields").getString("status"));
            } 
        	if (issue.getJSONObject("fields").has("project") && issue.getJSONObject("fields").get("project")!= null && issue.getJSONObject("fields").get("project") instanceof String) {
        		newIssue.setProjectId(issue.getJSONObject("fields").getString("project"));
            } 
        	if (issue.getJSONObject("fields").has("priority") && issue.getJSONObject("fields").get("priority")!= null && issue.getJSONObject("fields").get("priority") instanceof String) {
        		newIssue.setPriority(issue.getJSONObject("fields").getString("priority") );
            }
            

           
        	
            //String issueName = issue.getJSONObject("fields").getString("summary");

            //int userPoints = issue.getJSONObject("fields").optInt("customfield_10032", -1);

            //Issue currentIssue = new Issue();
            
            issueList.add(newIssue);

            
            DatabaseInsertion databaseInsertion = new DatabaseInsertion();
			// On utilise DatabaseInsertion pour insérer l'issue dans la base de données
            databaseInsertion.insertIssueIntoDatabase(newIssue);
            System.out.println("Issue inserted into databases, details : " + newIssue);
            

        }
    }
    
    
    

}