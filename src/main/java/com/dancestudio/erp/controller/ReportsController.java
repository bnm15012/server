package com.dancestudio.erp.controller;

import com.dancestudio.erp.response.ReportResponse;
import com.dancestudio.erp.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportsController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/{year}")
    public ReportResponse generateIncomeReport(@PathVariable Long year) {
        return reportService.generateIncomeReport(year);
    }
}
