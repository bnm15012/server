package com.dancestudio.erp.entry;

import lombok.Data;

@Data
public class DashboardEntry {

    private Long totalStudents;
    private Long totalInstructors;
    private Long totalActiveMemberships;

    private Long totalLastMonthExpenseCount;
    private double totalLastMonthExpenseAmount;
    
    private Long totalCurrentMonthExpenseCount;
    private double totalCurrentMonthExpenseAmount;

    private Long totalLastMonthPaymentCount;
    private double totalLastMonthPaymentAmount;
    
    private Long totalCurrentMonthPaymentCount;
    private double totalCurrentMonthPaymentAmount;
}
