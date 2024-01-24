package com.soprasteria.jira.agile.webapp.services.rules;

import java.util.List;

import org.springframework.stereotype.Component;

import com.soprasteria.jira.agile.webapp.models.Issue;

@Component
public class AnotherRule implements DataAnalysisRule{

	@Override
	public void test() {
		System.out.println("anotherrule");
		
	}

	@Override
	public float getWeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void getScore(List<Issue> issues) {
		// TODO Auto-generated method stub
		
	}

	

}
