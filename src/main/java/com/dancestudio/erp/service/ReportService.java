package com.dancestudio.erp.service;

import org.springframework.http.ResponseEntity;

import com.dancestudio.erp.enums.ReportType;
import com.dancestudio.erp.response.ReportResponse;

public interface ReportService {
    ResponseEntity<ReportResponse<?>> getReport(Long studioId, Long branchId, ReportType reportType, String startDate, String endDate, Integer page, Integer size);
}
