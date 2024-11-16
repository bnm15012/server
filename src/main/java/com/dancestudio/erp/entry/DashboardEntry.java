package com.dancestudio.erp.entry;

import lombok.Data;

@Data
public class DashboardEntry {

    private Long totalStudents;
    private Long totalInstructors;
    private Long totalActiveMemberships;

    private Long lastWeekIncome;
    private Long lastMonthIncome;

    private Long totalExpenseCount;
}
