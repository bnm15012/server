package com.dancestudio.erp.response;

import com.dancestudio.erp.entry.MonthlyReportEntry;
import com.dancestudio.erp.entry.ReportEntry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse extends AbstractResponse {
    private List<MonthlyReportEntry> data;
}