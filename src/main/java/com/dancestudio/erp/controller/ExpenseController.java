package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.ExpenseEntry;
import com.dancestudio.erp.response.ExpenseResponse;
import com.dancestudio.erp.service.BaseService;
import com.dancestudio.erp.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expenses")
public class ExpenseController extends BaseController<ExpenseEntry, ExpenseResponse, Long> {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/getAll/{branchId}")
    public ResponseEntity<ExpenseResponse> getAllExpenses(@PathVariable Long branchId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
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

    @Override
    protected BaseService<ExpenseEntry, ExpenseResponse, Long> getService() {
        return expenseService;
    }
}
