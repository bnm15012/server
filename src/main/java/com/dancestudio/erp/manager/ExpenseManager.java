package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.ExpenseEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;

import java.util.List;

public interface ExpenseManager {

    ExpenseEntry addExpense(ExpenseEntry expenseEntry) throws EntityNotFoundException;

    ExpenseEntry updateExpense(Long expenseId, ExpenseEntry expenseEntry) throws EntityNotFoundException;

    void deleteExpense(Long expenseId) throws EntityNotFoundException;

    ExpenseEntry getExpenseById(Long expenseId) throws EntityNotFoundException;

    List<ExpenseEntry> getAllExpenses(Long studioId, Long startMonth, Long endMonth);
}
