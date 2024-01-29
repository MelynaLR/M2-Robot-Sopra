package com.soprasteria.jira.agile.webapp.controller;
import com.soprasteria.jira.agile.webapp.models.Issue;
import com.soprasteria.jira.agile.webapp.services.DataAnalysisExcel;
import com.soprasteria.jira.agile.webapp.services.dbExcel;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/static/api/data")
public class ReactDataController {

    private final DataAnalysisExcel dataAnalysisExcel;

    public ReactDataController(DataAnalysisExcel dataAnalysisExcel) {
        this.dataAnalysisExcel = dataAnalysisExcel;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(produces = "application/json")
    public ResponseEntity<Object[]> getData() {
        try {
            // Retrieve data from the database
            List<Issue> issuesList = dbExcel.selectQueryData();

            // Get user points and user name from the service
            Integer score = dataAnalysisExcel.getOneLineFromQuery(issuesList);
            String user = dataAnalysisExcel.getUserNameFromQuery(issuesList);

            // Construct response object
            Object[] result = {score, user};
            return ResponseEntity.ok(result);
        } catch (SQLException e) {
            // Handle SQLException
            e.printStackTrace();
            // Return appropriate error response
            return ResponseEntity.status(500).body(new Object[0]); // Empty body or predefined error message
        }
    }
}
