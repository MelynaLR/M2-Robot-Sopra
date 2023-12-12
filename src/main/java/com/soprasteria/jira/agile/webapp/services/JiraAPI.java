package com.soprasteria.jira.agile.webapp.services;

import org.glassfish.jersey.client.ClientResponse;
import java.util.Base64;
import org.springframework.boot.autoconfigure.security.oauth2.server.servlet.OAuth2AuthorizationServerProperties.Client;
import com.sun.jersey.api.client.WebResource;

public class JiraAPI{
	
	
	private String username = "berryyannis@gmail.com"; // a mettre dans .properties
	private String tokenApi = "ATATT3xFfGF0W-C5fiUZ-EkE-St9eESZxHSyOuTi60rK5YJfufZPDiDaiWWNx05uUc8a8vv-0zs09IWUTrGjeiUs29uxMgDnftWX9jj0dXiAvdCru20h7Gel1SPwgKa-4YSx0qbYUcDEJyc67O1VSbTV-SEgahxnFAR_cNFWHgR7Pe4kLiopCAA=C572F4A4";
	private String headerType = "application/json";
	private String authorizationHeader;
	
	public void createAutorisationHeader() {
		this.authorizationHeader = "Basic "+Base64.getEncoder().encodeToString(this.username.getBytes())+ " :"+this.tokenApi;
	}
	
	
	
	
	
	
	
	
	
	 //String auth = new String(Base64.encode("username" + ":" + "password"));
	 
	/* 
	 final String headerAuthorization = "Authorization";
	 final String headerAuthorizationValue = "Basic " + auth;
	 final String headerType = "application/json";
	Client client = Client.create();

	WebResource webResource = client.resource("url");
	response =  webResource.header(headerAuthorization, headerAuthorizationValue).type(headerType).accept(headerType).get(ClientResponse.class);
*/
}