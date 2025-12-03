package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entry.*;
import com.dancestudio.erp.enums.PayeeType;
import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.manager.*;
import com.dancestudio.erp.modules.expense.Expense;
import com.dancestudio.erp.modules.expense.ExpenseConvertor;
import com.dancestudio.erp.modules.expense.ExpenseEntry;
import com.dancestudio.erp.modules.expense.ExpenseManager;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Setter(onMethod = @__({ @Autowired }))
@Component
public class ReportManagerImpl implements ReportManager {

    @Autowired
    private ExpenseManager expenseManager;
    @Autowired
    private PaymentManager paymentManager;
    @Autowired
    private BookingManager bookingManager;

    @Autowired
    private StudentActivityAssignmentManager studentActivityAssignmentManager;

    public List<MonthlyReportEntry> getAnalysisReport(Integer year, Long branchId) {
        return studentActivityAssignmentManager.getAnalysisReport(year, branchId);
    }

    @Override
    public IEReportEntry getReports(Long studioId, Long branchId, Integer startDate, Integer startMonth,
            Integer startYear, Integer endDate, Integer endMonth, Integer endYear) throws Exception {
        IEReportEntry reportEntry = new IEReportEntry();

        IEMonthlyReportEntry monthlyReports = processPaymentEntries(branchId, startDate, startMonth, startYear, endDate,
                endMonth, endYear);
        processExpenseEntries(branchId, startDate, startMonth, startYear, endDate, endMonth, endYear, monthlyReports);
        processBookingEntries(branchId, startDate, startMonth, startYear, endDate, endMonth, endYear, monthlyReports);

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

    private IEMonthlyReportEntry processPaymentEntries(Long branchId, Integer startDate, Integer startMonth,
            Integer startYear, Integer endDate, Integer endMonth, Integer endYear) throws Exception {
        IEMonthlyReportEntry monthlyReports = new IEMonthlyReportEntry();

        List<PaymentEntry> paymentEntries = paymentManager.getAllPaymentsByBranch(branchId, 0, -1, startDate,
                startMonth, startYear, endDate, endMonth, endYear, null, null);
        List<IncomeEntry> incomeEntries = paymentEntries.stream()
                .filter(paymentEntry -> paymentEntry.getPayeeType().equals(PayeeType.STUDENT))
                .map(this::extractIncomeEntry)
                .collect(Collectors.toList());

        double totalIncome = paymentEntries.stream()
                .mapToDouble(PaymentEntry::getAmount)
                .sum();

        monthlyReports.setIncomeEntries(incomeEntries);
        monthlyReports.setIncome(totalIncome);
        return monthlyReports;
    }

    private void processExpenseEntries(Long branchId, Integer startDate, Integer startMonth, Integer startYear,
            Integer endDate, Integer endMonth, Integer endYear, IEMonthlyReportEntry monthlyReports) throws Exception {

        Page<ExpenseEntry> entries = expenseManager.getAllExpenses(branchId, 0, -1, startDate, startMonth,
                startYear, endDate,
                endMonth, endYear, null);

        List<ExpenseEntry> expenseEntries = (entries.getContent());
        double totalExpense = expenseEntries.stream()
                .mapToDouble(ExpenseEntry::getAmount)
                .sum();

        monthlyReports.setExpenseEntries(expenseEntries);
        monthlyReports.setExpense(totalExpense);
    }

    private void processBookingEntries(Long branchId, Integer startDate, Integer startMonth, Integer startYear,
            Integer endDate, Integer endMonth, Integer endYear, IEMonthlyReportEntry monthlyReports) throws Exception {
        List<BookingEntry> bookingEntries = bookingManager.getAllBookings(branchId, 0, -1, startDate, startMonth,
                startYear, endDate, endMonth, endYear, null);
        double totalBooking = bookingEntries.stream()
                .filter(bookingEntry -> bookingEntry.getPaymentStatus().equals(PaymentStatus.COMPLETED))
                .mapToDouble(BookingEntry::getTotalAmount)
                .sum();

        monthlyReports.setBookingEntries(bookingEntries);
        monthlyReports.setBooking(totalBooking);
    }

    private IncomeEntry extractIncomeEntry(PaymentEntry paymentEntry) {
        IncomeEntry incomeEntry = new IncomeEntry();

        incomeEntry.setStudentName(paymentEntry.getStudentEntry().getName());
        incomeEntry.setAmount(paymentEntry.getAmount());
        incomeEntry.setPaymentMode(paymentEntry.getPaymentType().name());
        incomeEntry.setPaymenDate(paymentEntry.getPaymentDate());

        if (paymentEntry.getStudentEntry() != null) {
            Long activityAssignmentId = paymentEntry.getPayeeId();
            try {
                StudentActivityAssignmentEntry studentActivityAssignment = studentActivityAssignmentManager
                        .getById(activityAssignmentId);

                if (studentActivityAssignment.getActivityName() != null) {
                    incomeEntry.setActivityName(studentActivityAssignment.getActivityName());
                }
                if (studentActivityAssignment.getMembershipType() != null) {
                    incomeEntry.setMembershipType(studentActivityAssignment.getMembershipType());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return incomeEntry;
    }

}
