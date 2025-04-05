package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.MonthlyReportEntry;
import com.dancestudio.erp.entry.ReportEntry;

import java.util.List;

public interface ReportManager {

    List<MonthlyReportEntry> generateSalesReport(Long year, Long studioId);

}
