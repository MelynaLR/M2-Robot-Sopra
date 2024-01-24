package com.soprasteria.jira.agile.webapp.controller;

import org.springframework.web.bind.annotation.RestController;

import com.soprasteria.jira.agile.webapp.services.JiraAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


@RestController
public class MainController {
    @Autowired
    private JiraAPI jiraAPI;

    public static void main(String[] args) {
        MainController mainController = new MainController();

        mainController.retrieveData();

		try {
            
			String url = "https://m2-projet-annuel-robot.atlassian.net/jira/software/projects/KAN/boards/1?isInsightsOpen=true";
            Document document = Jsoup.connect(url).get();

            // Extract data based on HTML structure
            Elements dataElements = document.select(".your-data-class");
            for (Element element : dataElements) {
                System.out.println(element.text());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "/")
    public void retrieveData() {
        //System.out.println("coucou yannis");
        jiraAPI.createAuthorizationHeader();
        String urlTest = "https://m2-projet-annuel-robot.atlassian.net/rest/api/3/search?jql=";
        
        jiraAPI.sendRequestAPI(urlTest);
    }


 

}
