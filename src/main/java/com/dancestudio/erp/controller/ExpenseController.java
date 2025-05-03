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

    @GetMapping("/getAllExpenses/{studioId}/{startMonth}/{endMonth}")
    public ResponseEntity<ExpenseResponse> getAllExpenses(@PathVariable Long studioId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @PathVariable Long startMonth, @PathVariable Long endMonth) {
        Long currentYear = (long) java.time.Year.now().getValue();
        return expenseService.getAllExpenses(studioId, page, size, startMonth, currentYear, endMonth, currentYear);
    }
}
