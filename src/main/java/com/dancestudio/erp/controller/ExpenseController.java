package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.ExpenseEntry;
import com.dancestudio.erp.response.ExpenseResponse;
import com.dancestudio.erp.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/add")
    public ResponseEntity<ExpenseResponse> addExpense(@RequestBody ExpenseEntry expenseEntry) {
        return expenseService.addExpense(expenseEntry);
    }

    @PutMapping("/update/{expenseId}")
    public ResponseEntity<ExpenseResponse> updateExpense(@PathVariable Long expenseId, @RequestBody ExpenseEntry expenseEntry) {
        return expenseService.updateExpense(expenseId, expenseEntry);
    }

    @DeleteMapping("/delete/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long expenseId) {
        return expenseService.deleteExpense(expenseId);
    }

    @GetMapping("/get/{expenseId}")
    public ResponseEntity<ExpenseResponse> getExpenseById(@PathVariable Long expenseId) {
        return expenseService.getExpenseById(expenseId);
    }

    @GetMapping("/getAllExpenses/{studioId}/{startMonth}/{endMonth}")
    public ResponseEntity<ExpenseResponse> getAllExpenses(@PathVariable Long studioId, @PathVariable Long startMonth, @PathVariable Long endMonth) {
        return expenseService.getAllExpenses(studioId, startMonth, endMonth);
    }
}
