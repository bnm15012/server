package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.MonthlyReportEntry;
import com.dancestudio.erp.entry.ReportEntry;

import java.time.LocalDate;
import java.util.List;

public interface ReportManager {

    List<MonthlyReportEntry> getAnalysisReport(Long year, Long studioId);

    List<ReportEntry> getReports(LocalDate startDate, LocalDate endDate);
}
