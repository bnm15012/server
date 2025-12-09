package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entry.*;
import com.dancestudio.erp.enums.PayeeType;
import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.manager.*;
import com.dancestudio.erp.modules.booking.BookingEntry;
import com.dancestudio.erp.modules.booking.BookingManager;
import com.dancestudio.erp.modules.expense.ExpenseEntry;
import com.dancestudio.erp.modules.expense.ExpenseManager;
import com.dancestudio.erp.modules.member.student.StudentActivityAssignment.StudentActivityAssignmentEntry;
import com.dancestudio.erp.modules.member.student.StudentActivityAssignment.StudentActivityAssignmentManager;
import com.dancestudio.erp.modules.payments.PaymentManager;
import com.dancestudio.erp.modules.payments.entry.PaymentEntry;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

                IEMonthlyReportEntry monthlyReports = processPaymentEntries(branchId, startDate, startMonth, startYear,
                                endDate,
                                endMonth, endYear);
                processExpenseEntries(branchId, startDate, startMonth, startYear, endDate, endMonth, endYear,
                                monthlyReports);
                processBookingEntries(branchId, startDate, startMonth, startYear, endDate, endMonth, endYear,
                                monthlyReports);

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

                List<PaymentEntry> paymentEntries = paymentManager.getAll(branchId, 0, -1, toUtcDate(startDate,
                                startMonth, startYear), toUtcDate(endDate, endMonth, endYear), null).getContent();
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

        private void processExpenseEntries(Long branchId, Integer startDate, Integer startMonth, Integer startYear,
                        Integer endDate, Integer endMonth, Integer endYear, IEMonthlyReportEntry monthlyReports)
                        throws Exception {

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
                        Integer endDate, Integer endMonth, Integer endYear, IEMonthlyReportEntry monthlyReports)
                        throws Exception {
                Page<BookingEntry> bookingEntries = bookingManager.getAllBookings(branchId, 0, -1, startDate,
                                startMonth,
                                startYear, endDate, endMonth, endYear, null);
                double totalBooking = bookingEntries.stream()
                                .filter(bookingEntry -> bookingEntry.getPaymentStatus().equals(PaymentStatus.COMPLETED))
                                .mapToDouble(BookingEntry::getTotalAmount)
                                .sum();

                monthlyReports.setBookingEntries(bookingEntries.getContent());
                monthlyReports.setBooking(totalBooking);
        }

        public static Date toUtcDate(int day, int month, int year) {
                LocalDate localDate = LocalDate.of(year, month, day);
                ZonedDateTime zdt = localDate.atStartOfDay(ZoneOffset.UTC);
                return Date.from(zdt.toInstant());
        }

        private IncomeEntry extractIncomeEntry(PaymentEntry paymentEntry) {
                IncomeEntry incomeEntry = new IncomeEntry();

                // incomeEntry.setStudentName(paymentEntry.getStudentEntry().getName());
                incomeEntry.setAmount(paymentEntry.getAmount());
                incomeEntry.setPaymentMode(paymentEntry.getPaymentType().name());
                incomeEntry.setPaymenDate(paymentEntry.getPaymentDate());

                if (paymentEntry.getPayeeType().equals(PayeeType.STUDENT)) {
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
