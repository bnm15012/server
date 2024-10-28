package com.dancestudio.erp.service;

import com.dancestudio.erp.response.ReportResponse;

public interface ReportService {

    ReportResponse generateIncomeReport(Long year);
}
