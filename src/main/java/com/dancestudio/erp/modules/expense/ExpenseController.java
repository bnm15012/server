package com.dancestudio.erp.modules.expense;

import com.dancestudio.erp.base.BaseController;
import com.dancestudio.erp.base.BaseResponse;
import com.dancestudio.erp.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expenses")
public class ExpenseController extends BaseController<ExpenseEntry, Long> {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/getAll/{branchId}")
    public ResponseEntity<BaseResponse<ExpenseEntry>> getAllExpenses(@PathVariable Long branchId,
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
    protected BaseService<ExpenseEntry, Long> getService() {
        return expenseService;
    }
}
