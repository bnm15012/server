package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entry.*;
import com.dancestudio.erp.manager.ExpenseManager;
import com.dancestudio.erp.manager.PaymentManager;
import com.dancestudio.erp.manager.ReportManager;
import com.dancestudio.erp.manager.StudentActivityAssignmentManager;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@Setter(onMethod = @__({@Autowired}))
@Component
public class ReportManagerImpl implements ReportManager {

    @Autowired private ExpenseManager expenseManager;
    @Autowired private PaymentManager paymentManager;

    @Autowired
    private StudentActivityAssignmentManager studentActivityAssignmentManager;


    public List<MonthlyReportEntry> getAnalysisReport(Integer year, Long studioId) {
        return studentActivityAssignmentManager.getAnalysisReport(year, studioId);
    }

    @Override
    public IEReportEntry getReports(Long studioId, Long branchId, Integer startMonth, Integer startYear, Integer endMonth, Integer endYear) throws Exception {
        IEReportEntry reportEntry = new IEReportEntry();

        IEMonthlyReportEntry monthlyReports = processPaymentEntries(branchId, startMonth, startYear, endMonth, endYear);
        processExpenseEntries(branchId, startMonth, startYear, endMonth, endYear, monthlyReports);

        reportEntry.setStudioId(studioId);
        reportEntry.setBranchId(branchId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yyyy");
        YearMonth start = YearMonth.of(startYear.intValue(), startMonth.intValue());
        YearMonth end = YearMonth.of(endYear.intValue(), endMonth.intValue());

        reportEntry.setReportFrom(start.format(formatter));
        reportEntry.setReportTo(end.format(formatter));
        reportEntry.setIeMonthlyReportEntry(monthlyReports);

        return reportEntry;
    }

    private IEMonthlyReportEntry processPaymentEntries(Long branchId, Integer startMonth, Integer startYear, Integer endMonth, Integer endYear) throws Exception {
        IEMonthlyReportEntry monthlyReports = new IEMonthlyReportEntry();

        List<PaymentEntry> paymentEntries = paymentManager.getAllPaymentsByBranch(branchId, 0, -1, startMonth, startYear, endMonth, endYear, null);
        List<IncomeEntry> incomeEntries = paymentEntries.stream()
                .map(this::extractIncomeEntry)
                .collect(Collectors.toList());

        double totalIncome = paymentEntries.stream()
                .mapToDouble(PaymentEntry::getAmount)
                .sum();

        monthlyReports.setIncomeEntries(incomeEntries);
        monthlyReports.setIncome(totalIncome);
        return monthlyReports;
    }

    private void processExpenseEntries(Long branchId, Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, IEMonthlyReportEntry monthlyReports) throws Exception {
        List<ExpenseEntry> expenseEntries = expenseManager.getAllExpenses(branchId, 0, -1, startMonth.intValue(), startYear.intValue(), endMonth.intValue(), endYear.intValue());
        double totalExpense = expenseEntries.stream()
                .mapToDouble(ExpenseEntry::getAmount)
                .sum();

        monthlyReports.setExpenseEntries(expenseEntries);
        monthlyReports.setExpense(totalExpense);
    }

    private IncomeEntry extractIncomeEntry(PaymentEntry paymentEntry) {
        IncomeEntry incomeEntry = new IncomeEntry();

        incomeEntry.setStudentName(paymentEntry.getStudentEntry().getName());
        incomeEntry.setAmount(paymentEntry.getAmount());
        incomeEntry.setPaymentMode(paymentEntry.getPaymentType().name());

        if (paymentEntry.getStudentEntry() != null && paymentEntry.getStudentEntry().getEnrolledActivities() != null) {
            paymentEntry.getStudentEntry().getEnrolledActivities().stream().findFirst().ifPresent(activity -> {
                if (activity.getActivity() != null) {
                    incomeEntry.setActivityName(String.valueOf(activity.getActivity().getActivityType()));
                }
                incomeEntry.setMembershipType(String.valueOf(activity.getMembershipType()));
            });
        }

        return incomeEntry;
    }

}
