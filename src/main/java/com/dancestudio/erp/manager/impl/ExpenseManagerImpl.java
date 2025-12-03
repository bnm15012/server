package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.converter.ExpenseConvertor;
import com.dancestudio.erp.entity.Expense;
import com.dancestudio.erp.entry.ExpenseEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.ExpenseManager;
import com.dancestudio.erp.repository.ExpenseRepository;
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
public class ExpenseManagerImpl implements ExpenseManager {

    private final ExpenseRepository expenseRepository;

    public ExpenseManagerImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public ExpenseEntry add(ExpenseEntry expenseEntry) throws Exception {
        Expense expense =ExpenseConvertor.toEntity(expenseEntry, null);
        return ExpenseConvertor.toEntry(expenseRepository.save(expense));
    }

    @Override
    public ExpenseEntry update(Long expenseId, ExpenseEntry expenseEntry) throws Exception {
        Expense existingExpense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found"));

        Expense updatedExpense =ExpenseConvertor.toEntity(expenseEntry, existingExpense);
        return ExpenseConvertor.toEntry(expenseRepository.save(updatedExpense));
    }

    @Override
    public void delete(Long expenseId) throws EntityNotFoundException {
        expenseRepository.findById(expenseId)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found"));

        expenseRepository.deleteById(expenseId);
    }

    @Override
    public ExpenseEntry getById(Long expenseId) throws Exception {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found"));

        return ExpenseConvertor.toEntry(expense);
    }

    @Override
    public Page<Expense> getAllExpenses(
            Long branchId,
            Integer page,
            Integer size,
            Integer startDate,
            Integer startMonth,
            Integer startYear,
            Integer endDate,
            Integer endMonth,
            Integer endYear,
            String searchTerm) throws Exception {

        Pageable pageable = size == -1
                ? Pageable.unpaged()
                : PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "lastModifiedOn"));

        Page<Expense> entries;

        if (startDate != null && startMonth != null && startYear != null &&
                endDate != null && endMonth != null && endYear != null) {

            Map<String, Date> range = DateUtil.getUTCDateRange(
                    startDate, startMonth, startYear,
                    endDate, endMonth, endYear);

            entries = expenseRepository.findAllByBranchIdAndDateRange(
                    branchId,
                    range.get("start"),
                    range.get("end"),
                    pageable,
                    searchTerm);

        } else {
            entries = expenseRepository.findByBranchId(branchId, pageable, searchTerm);
        }
        return entries;
    }
}
