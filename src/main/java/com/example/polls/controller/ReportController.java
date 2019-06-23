package com.example.polls.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.polls.model.ReportAvaliacoes;
import com.example.polls.model.ReportVendas;
import com.example.polls.security.CurrentUser;
import com.example.polls.security.UserPrincipal;
import com.example.polls.service.ReportService;

/**
 * Created by rajeevkumarsingh on 20/11/17.
 */

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);


    @GetMapping("/dataReportAvaliacoes")
    public ArrayList<ReportAvaliacoes> findDataReportAvaliacoes(@CurrentUser UserPrincipal currentUser) {
        return reportService.findDataReportAvaliacoes(currentUser);
    }
    
    @GetMapping("/dataReportVendas")
    public ArrayList<ReportVendas> findDataReportVendas(@CurrentUser UserPrincipal currentUser) {
        return reportService.findDataReportVendas(currentUser);
    }
}
