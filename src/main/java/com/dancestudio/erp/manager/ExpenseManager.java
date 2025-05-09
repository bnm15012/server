package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.ExpenseEntry;

import java.util.List;

public interface ExpenseManager extends BaseManager<ExpenseEntry, Long> {

    Long countExpensesByBranchId(Long branchId);

    Long countExpensesByBranchIdAndMonth(Long branchId, Integer startMonth, Integer startYear,  Integer endMonth, Integer endYear);

    List<ExpenseEntry> getAllExpenses(Long branchId, Integer page, Integer size, Integer startMonth, Integer startYear, Integer endMonth, Integer endYear) throws Exception;

}
