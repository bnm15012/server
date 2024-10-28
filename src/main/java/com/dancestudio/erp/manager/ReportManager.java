package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.ReportEntry;

import java.util.List;

public interface ReportManager {

    List<ReportEntry> generateIncomeReport(Long year);

}
