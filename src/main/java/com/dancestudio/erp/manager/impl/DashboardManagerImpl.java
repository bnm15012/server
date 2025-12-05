package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entry.*;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.*;
import com.dancestudio.erp.modules.expense.ExpenseRepository;
import com.dancestudio.erp.modules.payments.repository.PaymentRepository;
import com.dancestudio.erp.repository.*;
import com.dancestudio.erp.util.DateUtil;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
@Setter(onMethod = @__({@Autowired}))
public class DashboardManagerImpl implements DashboardManager {

        private MemberRepository memberRepository;
        private ExpenseRepository expenseRepository;
        private PaymentRepository paymentRepository;
        private StudentActivityAssignmentRepository studentActivityAssignmentRepository;

        @Override
        public DashboardEntry getDashboardDetails(Long branchId, int currentMonth, int currentYear) throws EntityNotFoundException {
                DashboardEntry entry = new DashboardEntry();

                entry.setTotalStudents(memberRepository.totalStudentsByBranchId(branchId));
                entry.setTotalInstructors(memberRepository.totalInstructorsByBranchId(branchId));
                entry.setTotalActiveMemberships(
                                studentActivityAssignmentRepository.totalStudentActiveMembershipByStudioId(branchId));

                int lastMonth = (currentMonth == 1) ? 12 : currentMonth - 1;
                int lastMonthYear = (currentMonth == 1) ? currentYear - 1 : currentYear;

                Map<String, Date> currentMonthRange = DateUtil.getDateRangeByMonthYear(currentMonth, currentYear, currentMonth,currentYear);

                Map<String, Date> lastMonthRange = DateUtil.getDateRangeByMonthYear(lastMonth, lastMonthYear, lastMonth, lastMonthYear);
                // PaymentExpenseSummary currentPayment = paymentRepository.findCountAndTotalAmountByBranchAndDateRange(
                //                 branchId, currentMonthRange.get("start"), currentMonthRange.get("end"));
                // PaymentExpenseSummary lastPayment = paymentRepository.findCountAndTotalAmountByBranchAndDateRange(
                //                 branchId, lastMonthRange.get("start"), lastMonthRange.get("end"));

                // entry.setTotalCurrentMonthPaymentCount(currentPayment.getCount());
                // entry.setTotalCurrentMonthPaymentAmount(currentPayment.getTotalAmount());
                // entry.setTotalLastMonthPaymentCount(lastPayment.getCount());
                // entry.setTotalLastMonthPaymentAmount(lastPayment.getTotalAmount());

                PaymentExpenseSummary currentExpense = expenseRepository.findCountAndTotalAmountByBranchAndDateRange(
                                branchId, currentMonthRange.get("start"), currentMonthRange.get("end"));
                PaymentExpenseSummary lastExpense = expenseRepository.findCountAndTotalAmountByBranchAndDateRange(
                                branchId, lastMonthRange.get("start"), lastMonthRange.get("end"));

                entry.setTotalCurrentMonthExpenseCount(currentExpense.getCount());
                entry.setTotalCurrentMonthExpenseAmount(currentExpense.getTotalAmount());
                entry.setTotalLastMonthExpenseCount(lastExpense.getCount());
                entry.setTotalLastMonthExpenseAmount(lastExpense.getTotalAmount());

                entry.setCurrentMonthRevenue(entry.getTotalCurrentMonthPaymentAmount() - entry.getTotalCurrentMonthExpenseAmount());
                entry.setLastMonthRevenue(entry.getTotalLastMonthPaymentAmount() - entry.getTotalLastMonthExpenseAmount());
                return entry;
        }

}
