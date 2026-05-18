package com.dancestudio.erp.modules.expense;

import com.dancestudio.erp.base.BaseManager;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.util.DateUtil;

import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
@Setter
public class ExpenseManager extends BaseManager<Expense, Long, ExpenseEntry> {

    private final ExpenseRepository expenseRepository;

    public ExpenseManager(ExpenseRepository expenseRepository) {
        super(expenseRepository, "Expense");
        this.expenseRepository = expenseRepository;
    }

    public Page<ExpenseEntry> getAllExpenses(
            Long branchId,
            Integer page,
            Integer size,
            Integer startDate,
            Integer startMonth,
            Integer startYear,
            Integer endDate,
            Integer endMonth,
            Integer endYear,
            String searchTerm) {

        Pageable pageable = size == -1
                ? Pageable.unpaged()
                : PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "lastModifiedOn"));

        Page<Expense> expensesPage;

        if (startDate != null && startMonth != null && startYear != null &&
                endDate != null && endMonth != null && endYear != null) {

            Map<String, Date> range = DateUtil.getUTCDateRange(
                    startDate, startMonth, startYear,
                    endDate, endMonth, endYear);

            expensesPage = expenseRepository.findAllByBranchIdAndDateRange(
                    branchId,
                    range.get("start"),
                    range.get("end"),
                    pageable,
                    searchTerm);
        } else {
            expensesPage = expenseRepository.findByBranchId(branchId, pageable, searchTerm);
        }

        return expensesPage.map(ExpenseConvertor::toEntry);
    }

    @Override
    protected Expense toEntity(ExpenseEntry entry, Expense existing) throws EntityNotFoundException {
        return ExpenseConvertor.toEntity(entry, existing);
    }

    @Override
    protected ExpenseEntry toEntry(Expense entity, String[] fields) throws EntityNotFoundException {
        return ExpenseConvertor.toEntry(entity);
    }
}
