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

@Setter(onMethod = @__({ @Autowired }))
@Component
public class ExpenseServiceImpl implements ExpenseService {

    private ExpenseManager expenseManager;

    @Override
    public ResponseEntity<ExpenseResponse> add(ExpenseEntry expenseEntry) {
        ExpenseResponse response = new ExpenseResponse();

        try {
            ExpenseEntry entry = expenseManager.add(expenseEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Expense added successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<ExpenseResponse> update(Long expenseId, ExpenseEntry expenseEntry) {
        ExpenseResponse response = new ExpenseResponse();

        try {
            ExpenseEntry entry = expenseManager.update(expenseId, expenseEntry);

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
    public ResponseEntity<Void> delete(Long expenseId) {
        try {
            expenseManager.delete(expenseId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<ExpenseResponse> get(Long expenseId) {
        ExpenseResponse response = new ExpenseResponse();

        try {
            ExpenseEntry entry = expenseManager.getById(expenseId);

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
    public ResponseEntity<ExpenseResponse> getAllExpenses(Long branchId, Integer page, Integer size, Integer startMonth,
            Integer startYear, Integer endMonth, Integer endYear) {
        ExpenseResponse response = new ExpenseResponse();

        try {
            List<ExpenseEntry> entries = expenseManager.getAllExpenses(branchId, page, size, startMonth, startYear,
                    endMonth, endYear);

            long expenseCount = (startMonth.equals(0) || endMonth.equals(0) || startYear.equals(0)
                    || endYear.equals(0)) ? expenseManager.countExpensesByBranchId(branchId)
                            : expenseManager.countExpensesByBranchIdAndMonth(branchId, startMonth, startYear, endMonth,
                                    endYear);
            response.setData(entries);
            response.setStatus(new StatusResponse(1, "Expenses retrieved successfully", StatusResponse.Type.SUCCESS,
                    (int) expenseCount));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
