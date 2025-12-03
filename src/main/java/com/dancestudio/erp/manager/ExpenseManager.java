package com.dancestudio.erp.manager;

import com.dancestudio.erp.entity.Expense;
import com.dancestudio.erp.entry.ExpenseEntry;

import org.springframework.data.domain.Page;

public interface ExpenseManager extends BaseManagerInt<ExpenseEntry, Long> {
    Page<Expense> getAllExpenses(Long branchId, Integer page, Integer size, Integer startDate, Integer startMonth, Integer startYear, Integer endDate, Integer endMonth, Integer endYear, String searchTerm) throws Exception;
}
