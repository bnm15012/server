package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.ExpenseEntry;
import com.dancestudio.erp.response.ExpenseResponse;
import org.springframework.http.ResponseEntity;

public interface ExpenseService extends BaseService<ExpenseEntry, ExpenseResponse, Long> {

    ResponseEntity<ExpenseResponse> getAllExpenses(Long studioId, int page, int size, Long startMonth, Long endMonth);
}
