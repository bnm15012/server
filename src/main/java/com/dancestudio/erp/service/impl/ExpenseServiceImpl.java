package com.dancestudio.erp.service.impl;


import com.dancestudio.erp.entry.ExpenseEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.ExpenseManager;
import com.dancestudio.erp.response.ExpenseResponse;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.service.ExpenseService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Setter(onMethod = @__({@Autowired}))
@Component
public class ExpenseServiceImpl implements ExpenseService {

    private ExpenseManager expenseManager;

    @Override
    public ResponseEntity<ExpenseResponse> addExpense(ExpenseEntry expenseEntry) {
        ExpenseResponse response = new ExpenseResponse();

        try {
            ExpenseEntry entry = expenseManager.addExpense(expenseEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Expense added successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<ExpenseResponse> updateExpense(Long expenseId, ExpenseEntry expenseEntry) {
        ExpenseResponse response = new ExpenseResponse();

        try {
            ExpenseEntry entry = expenseManager.updateExpense(expenseId, expenseEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Expense updated successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EntityNotFoundException e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<Void> deleteExpense(Long expenseId) {
        try {
            expenseManager.deleteExpense(expenseId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<ExpenseResponse> getExpenseById(Long expenseId) {
        ExpenseResponse response = new ExpenseResponse();

        try {
            ExpenseEntry entry = expenseManager.getExpenseById(expenseId);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Expense retrieved successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EntityNotFoundException e) {
            response.setData(Collections.emptyList());
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<ExpenseResponse> getAllExpenses(Long studioId, Long startMonth, Long endMonth) {
        ExpenseResponse response = new ExpenseResponse();

        try {
            List<ExpenseEntry> entries = expenseManager.getAllExpenses(studioId, startMonth, endMonth);

            response.setData(entries);
            response.setStatus(new StatusResponse(1, "Expenses retrieved successfully", StatusResponse.Type.SUCCESS, entries.size()));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
