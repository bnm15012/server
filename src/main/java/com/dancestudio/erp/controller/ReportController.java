package com.dancestudio.erp.controller;

import com.dancestudio.erp.enums.ReportType;
import com.dancestudio.erp.response.*;
import com.dancestudio.erp.service.ReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("get/report/{report_type}/{studioId}")
    public ResponseEntity<ReportResponse<?>> getReports(
            @PathVariable Long studioId,
            @PathVariable ReportType report_type,
            @RequestParam(required = false) Long branchId,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return reportService.getReport(studioId, branchId, report_type, startDate, endDate, page, size);
    }
}
