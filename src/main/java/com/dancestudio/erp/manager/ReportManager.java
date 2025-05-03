package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.MonthlyReportEntry;
import com.dancestudio.erp.entry.IEReportEntry;

import java.util.List;

public interface ReportManager {

    List<MonthlyReportEntry> getAnalysisReport(Long year, Long studioId);

    IEReportEntry getReports(Long studioId, Long branchId, Long startMonth, Long startYear, Long endMonth, Long endYear) throws Exception;
}
