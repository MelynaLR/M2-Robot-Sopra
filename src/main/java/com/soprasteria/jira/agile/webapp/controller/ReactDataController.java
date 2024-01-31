package com.soprasteria.jira.agile.webapp.controller;

import com.soprasteria.jira.agile.webapp.models.Issue;
import com.soprasteria.jira.agile.webapp.services.DataAnalysisExcel;
import com.soprasteria.jira.agile.webapp.services.dbExcel;
import com.soprasteria.jira.agile.webapp.services.ScoreCalculation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/static/api/data")
public class ReactDataController {

    private final DataAnalysisExcel dataAnalysisExcel;

    public ReactDataController(DataAnalysisExcel dataAnalysisExcel, ScoreCalculation scoreCalculation) {
        this.dataAnalysisExcel = dataAnalysisExcel;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(produces = "application/json")
    public ResponseEntity<Object[]> getData() {
        try {
          
            List<Issue> issuesList = dbExcel.selectQueryData();
            Integer score = dataAnalysisExcel.getOneLineFromQuery(issuesList);
            String user = dataAnalysisExcel.getUserNameFromQuery(issuesList);
          
           // int globalScore = scoreCalculation.calculateGlobalScore();

            Object[] result = {score, user};
            return ResponseEntity.ok(result);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new Object[0]); 
        }
    }
}
