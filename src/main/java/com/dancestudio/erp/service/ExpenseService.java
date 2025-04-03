package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.ExpenseEntry;
import com.dancestudio.erp.response.ExpenseResponse;
import org.springframework.http.ResponseEntity;

public interface ExpenseService {

    ResponseEntity<ExpenseResponse> addExpense(ExpenseEntry expenseEntry);

    ResponseEntity<ExpenseResponse> updateExpense(Long expenseId, ExpenseEntry expenseEntry);

    ResponseEntity<Void> deleteExpense(Long expenseId);

    ResponseEntity<ExpenseResponse> getExpenseById(Long expenseId);

    ResponseEntity<ExpenseResponse> getAllExpenses(Long studioId, int page, int size, Long startMonth, Long endMonth);
}
