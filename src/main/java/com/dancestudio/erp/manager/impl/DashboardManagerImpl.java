package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entry.*;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.*;
import com.dancestudio.erp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DashboardManagerImpl implements DashboardManager {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private StudentActivityAssignmentManager studentActivityAssignmentManager;

    @Autowired
    private StudentActivityAssignmentRepository activityAssignmentRepository;

    @Override
    public DashboardEntry getDashboardDetails(Long studioId, Long startMonth, Long endMonth) throws EntityNotFoundException {
        DashboardEntry entry = new DashboardEntry();

        entry.setTotalStudents(studentRepository.totalStudentsByStudioId(studioId));
        entry.setTotalInstructors(instructorRepository.totalInstructorsByStudioId(studioId));
        entry.setTotalActiveMemberships(activityAssignmentRepository.totalStudentActiveMembershipByStudioId(studioId));
        entry.setMonthlyReportEntries(studentActivityAssignmentManager.calculateSalesReport((long) LocalDate.now().getYear(), studioId));

        int currentMonth = LocalDate.now().getMonthValue();

        processAmountCountEntries(entry, currentMonth, expenseRepository.countAndSumExpensesForCurrentAndLastMonth(studioId), true);
        processAmountCountEntries(entry, currentMonth, paymentRepository.countAndSumPaymentsForCurrentAndLastMonth(studioId), false);

        return entry;
    }

    private void processAmountCountEntries(DashboardEntry entry, int currentMonth, List<Object[]> data, boolean isExpense) {
        int lastMonth = (currentMonth == 1) ? 12 : currentMonth - 1;

        for (Object[] row : data) {
            int month = ((Number) row[0]).intValue();
            long count = ((Number) row[1]).longValue();
            double totalAmount = row[2] != null ? ((Number) row[2]).doubleValue() : 0.0;

            if (month == currentMonth) {
                setAmountCount(entry, count, totalAmount, isExpense, true);
            } else if (month == lastMonth) {
                setAmountCount(entry, count, totalAmount, isExpense, false);
            }
        }
    }

    private void setAmountCount(DashboardEntry entry, long count, double amount, boolean isExpense, boolean isCurrentMonth) {
        if (isExpense) {
            if (isCurrentMonth) {
                entry.setTotalCurrentMonthExpenseCount(count);
                entry.setTotalCurrentMonthExpenseAmount(amount);
            } else {
                entry.setTotalLastMonthExpenseCount(count);
                entry.setTotalLastMonthExpenseAmount(amount);
            }
        } else {
            if (isCurrentMonth) {
                entry.setTotalCurrentMonthPaymentCount(count);
                entry.setTotalCurrentMonthPaymentAmount(amount);
            } else {
                entry.setTotalLastMonthPaymentCount(count);
                entry.setTotalLastMonthPaymentAmount(amount);
            }
        }
    }
}
