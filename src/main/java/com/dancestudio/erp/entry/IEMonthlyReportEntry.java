package com.dancestudio.erp.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IEMonthlyReportEntry {

    private double income;
    private double expense;
    private double booking;
    private List<ExpenseEntry> expenseEntries;
    private List<IncomeEntry> incomeEntries;
    private List<BookingEntry> bookingEntries;

}
