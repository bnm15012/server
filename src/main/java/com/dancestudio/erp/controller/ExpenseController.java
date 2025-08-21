package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.ExpenseEntry;
import com.dancestudio.erp.response.ExpenseResponse;
import com.dancestudio.erp.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expenses")
public class ExpenseController extends BaseController<ExpenseEntry, ExpenseResponse, Long> {

    @Autowired
    private ExpenseService expenseService;

    @Override
    public ResponseEntity<ExpenseResponse> add(@RequestBody ExpenseEntry expenseEntry) {
        return expenseService.add(expenseEntry);
    }

    @Override
    public ResponseEntity<ExpenseResponse> update(@PathVariable Long id, @RequestBody ExpenseEntry expenseEntry) {
        return expenseService.update(id, expenseEntry);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return expenseService.delete(id);
    }

    @Override
    public ResponseEntity<ExpenseResponse> get(@PathVariable Long id) {
        return expenseService.get(id);
    }

    @GetMapping("/getAllExpenses/{branchId}")
    public ResponseEntity<ExpenseResponse> getAllExpenses(@PathVariable Long branchId,
            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Integer startDate,
            @RequestParam(required = false) Integer startMonth,
            @RequestParam(required = false) Integer startYear,
            @RequestParam(required = false) Integer endDate,
            @RequestParam(required = false) Integer endMonth,
            @RequestParam(required = false) Integer endYear,
            @RequestParam(required = false) String searchTerm) {
        return expenseService.getAllExpenses(branchId, page, size, startDate, startMonth, startYear, endDate, endMonth,
                endYear, searchTerm);
    }
}
