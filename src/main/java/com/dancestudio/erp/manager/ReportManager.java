package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.MonthlyReportEntry;
import com.dancestudio.erp.entry.IEReportEntry;

import java.util.List;

public interface ReportManager {

    List<MonthlyReportEntry> getAnalysisReport(Integer year, Long branchId);

    IEReportEntry getReports(Long studioId, Long branchId, Integer startDate, Integer startMonth, Integer startYear, Integer endDate, Integer endMonth, Integer endYear) throws Exception;
}
