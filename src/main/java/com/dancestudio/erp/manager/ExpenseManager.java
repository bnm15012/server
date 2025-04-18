package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.ExpenseEntry;

import java.util.List;

public interface ExpenseManager extends BaseManager<ExpenseEntry, Long> {

    Long countExpensesByStudioId(Long studioId);

    Long countExpensesByStudioIdAndMonth(Long studioId, Long startMonth, Long endMonth);

    List<ExpenseEntry> getAllExpenses(Long studioId, int page, int size, Long startMonth, Long endMonth) throws Exception;
}
