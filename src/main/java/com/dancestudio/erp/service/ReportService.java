package com.dancestudio.erp.service;

import com.dancestudio.erp.response.ReportResponse;
import org.springframework.http.ResponseEntity;

public interface ReportService {

    ResponseEntity<ReportResponse> getAnalysisReport(Long year, Long studioId);

    ResponseEntity<ReportResponse> getReports(String startDate, String endDate);

}
