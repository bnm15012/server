package com.dancestudio.erp.service;

import com.dancestudio.erp.response.ReportResponse;
import org.springframework.http.ResponseEntity;

public interface ReportService {

    ResponseEntity<ReportResponse> generateIncomeReport(Long year);
}
