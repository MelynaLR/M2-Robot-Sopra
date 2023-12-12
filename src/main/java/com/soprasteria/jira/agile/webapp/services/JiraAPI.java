package com.soprasteria.jira.agile.webapp.services;

import org.glassfish.jersey.client.ClientResponse;
import java.util.Base64;
import org.springframework.boot.autoconfigure.security.oauth2.server.servlet.OAuth2AuthorizationServerProperties.Client;

public class JiraAPI{
	
	
	private String username = "berryyannis@gmail.com";
	
	ClientResponse response;
	String auth = Base64.getEncoder().encodeToString(this.username.getBytes());
	
	
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