package com.soprasteria.jira.agile.webapp.models;

public class Issue {
    private String name;
    private int userPoints;

    // Constructors, getters, and setters...

    public Issue(String name, int userPoints) {
        this.name = name;
        this.userPoints = userPoints;
    }

    // Other methods or additional fields as needed...

    @Override
    public String toString() {
        return "Issue{" +
                "name='" + name + '\'' +
                ", userPoints=" + userPoints +
                '}';
    }
}
