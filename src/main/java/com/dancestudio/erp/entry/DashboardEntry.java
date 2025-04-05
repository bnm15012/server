package com.dancestudio.erp.entry;

import lombok.Data;

import java.util.List;

@Data
public class DashboardEntry {

    private Long totalStudents;
    private Long totalInstructors;
    private Long totalActiveMemberships;

    private List<MonthlyReportEntry> monthlyReportEntries;

    private Long totalLastMonthExpenseCount;
    private double totalLastMonthExpenseAmount;

    private Long totalCurrentMonthExpenseCount;
    private double totalCurrentMonthExpenseAmount;

    private Long totalLastMonthPaymentCount;
    private double totalLastMonthPaymentAmount;

    private Long totalCurrentMonthPaymentCount;
    private double totalCurrentMonthPaymentAmount;
}
