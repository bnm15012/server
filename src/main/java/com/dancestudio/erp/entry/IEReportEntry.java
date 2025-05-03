package com.dancestudio.erp.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IEReportEntry {

    private Long studioId;
    private Long branchId;

    private String reportFrom;
    private String reportTo;
    private IEMonthlyReportEntry ieMonthlyReportEntry;
}
