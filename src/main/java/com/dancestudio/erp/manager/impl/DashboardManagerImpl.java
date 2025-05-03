package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entry.*;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.*;
import com.dancestudio.erp.repository.*;
import com.dancestudio.erp.util.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Service
public class DashboardManagerImpl implements DashboardManager {

        @Autowired
        private MemberRepository memberRepository;

        @Autowired
        private ExpenseRepository expenseRepository;

        @Autowired
        private PaymentRepository paymentRepository;

        @Autowired
        private StudentActivityAssignmentRepository studentActivityAssignmentRepository;

        @Override
        public DashboardEntry getDashboardDetails(Long branchId, int currentMonth, int currentYear, String userTimeZone) throws EntityNotFoundException {
                DashboardEntry entry = new DashboardEntry();

                entry.setTotalStudents(memberRepository.totalStudentsByBranchId(branchId));
                entry.setTotalInstructors(memberRepository.totalInstructorsByBranchId(branchId));
                entry.setTotalActiveMemberships(
                                studentActivityAssignmentRepository.totalStudentActiveMembershipByStudioId(branchId));

                int lastMonth = (currentMonth == 1) ? 12 : currentMonth - 1;
                int lastMonthYear = (currentMonth == 1) ? currentYear - 1 : currentYear;

                Map<String, Date> currentMonthRange = DateUtil.getRange(currentMonth, currentYear, currentMonth,
                                currentYear,
                                ZoneId.of(userTimeZone));

                Map<String, Date> lastMonthRange = DateUtil.getRange(lastMonth, lastMonthYear, lastMonth, lastMonthYear,
                                ZoneId.of(userTimeZone));
                // Payment data for the current and last month
                PaymentExpenseSummary currentPayment = paymentRepository.findCountAndTotalAmountByBranchAndDateRange(
                                branchId, currentMonthRange.get("start"), currentMonthRange.get("end"));
                PaymentExpenseSummary lastPayment = paymentRepository.findCountAndTotalAmountByBranchAndDateRange(
                                branchId, lastMonthRange.get("start"), lastMonthRange.get("end"));

                // // Safely handle the values
                entry.setTotalCurrentMonthPaymentCount(currentPayment.getCount());
                entry.setTotalCurrentMonthPaymentAmount(currentPayment.getTotalAmount());
                entry.setTotalLastMonthPaymentCount(lastPayment.getCount());
                entry.setTotalLastMonthPaymentAmount(lastPayment.getTotalAmount());

                // Expense data for the current and last month
                PaymentExpenseSummary currentExpense = expenseRepository.findCountAndTotalAmountByBranchAndDateRange(
                                branchId, currentMonthRange.get("start"), currentMonthRange.get("end"));
                PaymentExpenseSummary lastExpense = expenseRepository.findCountAndTotalAmountByBranchAndDateRange(
                                branchId, lastMonthRange.get("start"), lastMonthRange.get("end"));

                // Safely handle the values for expenses
                entry.setTotalCurrentMonthExpenseCount(currentExpense.getCount());
                entry.setTotalCurrentMonthExpenseAmount(currentExpense.getTotalAmount());
                entry.setTotalLastMonthExpenseCount(lastExpense.getCount());
                entry.setTotalLastMonthExpenseAmount(lastExpense.getTotalAmount());

                return entry;
        }

}
