package com.soprasteria.jira.agile.webapp.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Base64;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Component
public class JiraAPI {

    private String username = "berryyannis@gmail.com";
    private String tokenApi = "ATATT3xFfGF0W-C5fiUZ-EkE-St9eESZxHSyOuTi60rK5YJfufZPDiDaiWWNx05uUc8a8vv-0zs09IWUTrGjeiUs29uxMgDnftWX9jj0dXiAvdCru20h7Gel1SPwgKa-4YSx0qbYUcDEJyc67O1VSbTV-SEgahxnFAR_cNFWHgR7Pe4kLiopCAA=C572F4A4";
    private String headerType = "application/json";
    private String headerAuthorization = "Authorization";
    private String headerValue;

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
        JSONObject json = new JSONObject(responseBody);

        // Assuming the issues are stored in an array called "issues"
        JSONArray issuesArray = json.getJSONArray("issues");

        for (int i = 0; i < issuesArray.length(); i++) {
            JSONObject issue = issuesArray.getJSONObject(i);
            String issueName = issue.getJSONObject("fields").getString("summary");

            // Replace "customfield_XXXXX" with the actual field ID for "user points" in Jira
            int userPoints = issue.getJSONObject("fields").optInt("customfield_10032", -1);

            // Print or process the issue name and "user points" as needed
            System.out.println("Issue Name: " + issueName);
            System.out.println("User Points: " + userPoints);
        }
    }
}
