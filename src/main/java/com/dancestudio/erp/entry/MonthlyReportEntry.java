package com.dancestudio.erp.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyReportEntry {

    private int month;
    private int yearMonth;
    private double income;
    private double expense;
    private List<ExpenseEntry> expenseEntries;
    private List<PaymentEntry> paymentEntries;
    private double revenue;
    private List<Activity> activity;

    public MonthlyReportEntry(int month, double revenue) {
        this.month = month;
        this.revenue = revenue;
    }

    public static class Activity {
        private String name;
        private int participants;
    }
}
