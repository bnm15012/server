package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.ExpenseEntry;

import java.util.List;

public interface ExpenseManager extends BaseManager<ExpenseEntry, Long> {

    Long countExpensesByBranchId(Long branchId);

    Long countExpensesByBranchIdAndMonth(Long branchId, Long startMonth, Long endMonth);

    List<ExpenseEntry> getAllExpenses(Long branchId, int page, int size, Long startMonth, Long endMonth) throws Exception;
}
