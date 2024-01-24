package com.soprasteria.jira.agile.webapp.controller;

import com.soprasteria.jira.agile.webapp.services.DataAnalysisExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/static/api/data")
public class ReactDataController {

    private final DataAnalysisExcel dataAnalysisExcel;

    @Autowired
    public ReactDataController(DataAnalysisExcel dataAnalysisExcel) {
        this.dataAnalysisExcel = dataAnalysisExcel;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(produces = "application/json")
    public ResponseEntity<Object[]> getData() {
        Integer score = dataAnalysisExcel.getOneLineFromQuery();
        String user = dataAnalysisExcel.getUserNameFromQuery();
        
        Object[] result = {score, user};
        return ResponseEntity.ok(result);
    }
}
