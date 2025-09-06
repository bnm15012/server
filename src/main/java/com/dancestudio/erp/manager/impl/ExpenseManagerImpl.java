package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.Expense;
import com.dancestudio.erp.entry.BranchEntry;
import com.dancestudio.erp.entry.ExpenseEntry;
import com.dancestudio.erp.enums.ExpenseCategory;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.BranchManager;
import com.dancestudio.erp.manager.ExpenseManager;
import com.dancestudio.erp.repository.ExpenseRepository;
import com.dancestudio.erp.util.ConvertToEntryUtil;
import com.dancestudio.erp.util.DateUtil;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Setter
public class ExpenseManagerImpl implements ExpenseManager {

    private final ExpenseRepository expenseRepository;

    @Autowired
    private BranchManager branchManager;

    @Autowired
    public ExpenseManagerImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public ExpenseEntry add(ExpenseEntry expenseEntry) throws Exception {
        Expense expense = convertToEntity(expenseEntry, null);
        return convertToEntry(expenseRepository.save(expense));
    }

    @Override
    public ExpenseEntry update(Long expenseId, ExpenseEntry expenseEntry) throws Exception {
        Expense existingExpense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found"));

        Expense updatedExpense = convertToEntity(expenseEntry, existingExpense);
        return convertToEntry(expenseRepository.save(updatedExpense));
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

        return convertToEntry(expense);
    }

    @Override
    public List<ExpenseEntry> getAllExpenses(Long branchId, Integer page, Integer size, Integer startDate, Integer startMonth, Integer startYear, Integer endDate, Integer endMonth, Integer endYear, String searchTerm) throws Exception {

        Page<Expense> entries;
        Pageable pageable = size == -1 ? Pageable.unpaged() : PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "lastModifiedOn"));
        if (Objects.nonNull(startDate) && Objects.nonNull(startMonth) && Objects.nonNull(startYear) && Objects.nonNull(endDate) && Objects.nonNull(endMonth) && Objects.nonNull(endYear)) {

            Map<String, Date> monthRange = DateUtil.getUTCDateRange(startDate, startMonth, startYear, endDate, endMonth, endYear);
            entries = expenseRepository.findAllByBranchIdAndDateRange(branchId, monthRange.get("start"),
                    monthRange.get("end"), pageable, searchTerm);
        } else {
            List<Expense> expenses = expenseRepository.findByBranchId(branchId, pageable, searchTerm).getContent();
            return expenses.stream()
                    .map(expense -> {
                        try {
                            return convertToEntry(expense);
                        } catch (Exception e) {
                            throw new RuntimeException("Error converting Expense to entry", e);
                        }
                    })
                    .toList();
        }

        List<ExpenseEntry> expenseEntries = new ArrayList<>();
        for (Expense entry : entries) {
            ExpenseEntry expenseEntry = convertToEntry(entry);
            expenseEntries.add(expenseEntry);
        }

        return expenseEntries;
    }

    public ExpenseEntry convertToEntry(Expense expense) throws Exception {

        ExpenseEntry expenseEntry = new ExpenseEntry();
        expenseEntry.setExpenseId(expense.getId());
        expenseEntry.setAmount(expense.getAmount());
        expenseEntry.setDescription(expense.getDescription());

        BranchEntry branchEntry = branchManager.getById(expense.getBranch().getId());
        expenseEntry.setBranchId(branchEntry.getBranchId());

        expenseEntry.setExpenseDate(expense.getExpenseDate());
        expenseEntry.setExpenseCategory(ExpenseCategory.valueOf(expense.getExpenseCategory()));

        return expenseEntry;
    }

    @Override
    public Long countExpensesByBranchId(Long branchId) {
        return expenseRepository.countByBranchId(branchId);
    }

    @Override
    public Long countExpensesByBranchIdAndMonth(Long branchId, Integer startMonth, Integer startYear, Integer endMonth,
            Integer endYear, String searchTerm) {
        Map<String, Date> monthRange = DateUtil.getDateRangeByMonthYear(startMonth, startYear, endMonth, endYear);
        return expenseRepository.countByBranchIdAndDateRange(branchId, monthRange.get("start"), monthRange.get("end"),
                searchTerm);
    }

    private Expense convertToEntity(ExpenseEntry expenseEntry, Expense existingExpense) throws Exception {
        Expense expense = (existingExpense != null) ? existingExpense : new Expense();

        if (Objects.nonNull(expenseEntry.getExpenseId())) {
            expense.setId(expenseEntry.getExpenseId());
        }
        if (Objects.nonNull(expenseEntry.getAmount())) {
            expense.setAmount(expenseEntry.getAmount());
        }
        if (Objects.nonNull(expenseEntry.getDescription())) {
            expense.setDescription(expenseEntry.getDescription());
        }
        if (Objects.nonNull(expenseEntry.getBranchId())) {
            BranchEntry branchEntry = branchManager.getById(expenseEntry.getBranchId());
            expense.setBranch(ConvertToEntryUtil.convertToEntity(branchEntry, null));
        }
        if (Objects.nonNull(expenseEntry.getExpenseDate())) {
            expense.setExpenseDate(expenseEntry.getExpenseDate());
        }
        if (Objects.nonNull(expenseEntry.getExpenseCategory())) {
            expense.setExpenseCategory(expenseEntry.getExpenseCategory().name());
        }

        return expense;
    }
}
