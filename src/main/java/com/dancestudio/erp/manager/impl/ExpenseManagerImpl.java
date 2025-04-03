package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.Expense;
import com.dancestudio.erp.entry.ExpenseEntry;
import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.enums.ExpenseCategory;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.ExpenseManager;
import com.dancestudio.erp.manager.StudioManager;
import com.dancestudio.erp.repository.ExpenseRepository;
import com.dancestudio.erp.util.ConvertToEntryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ExpenseManagerImpl implements ExpenseManager {
    private final ExpenseRepository expenseRepository;

    @Autowired
    private StudioManager studioManager;

    @Autowired
    public ExpenseManagerImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public ExpenseEntry addExpense(ExpenseEntry expenseEntry) throws EntityNotFoundException {
        Expense expense = convertToEntity(expenseEntry, null);
        return convertToEntry(expenseRepository.save(expense));
    }

    @Override
    public ExpenseEntry updateExpense(Long expenseId, ExpenseEntry expenseEntry) throws EntityNotFoundException {
        Expense existingExpense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found"));

        Expense updatedExpense = convertToEntity(expenseEntry, existingExpense);
        return convertToEntry(expenseRepository.save(updatedExpense));
    }

    @Override
    public void deleteExpense(Long expenseId) throws EntityNotFoundException {
        expenseRepository.findById(expenseId)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found"));

        expenseRepository.deleteById(expenseId);
    }

    @Override
    public ExpenseEntry getExpenseById(Long expenseId) throws EntityNotFoundException {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found"));

        return convertToEntry(expense);
    }

    @Override
    public List<ExpenseEntry> getAllExpenses(Long studioId, int page, int size, Long startMonth, Long endMonth) throws EntityNotFoundException {
        Page<Expense> entries;
        Pageable pageable = PageRequest.of(page, size);
        if (startMonth.equals(0L) || endMonth.equals(0L)) {
            entries = expenseRepository.findExpensesByStudioId(studioId, pageable);
        } else {
            entries = expenseRepository.findAllByStudioId(studioId, startMonth, endMonth, pageable);
        }

        List<ExpenseEntry> expenseEntries = new ArrayList<>();
        for (Expense entry : entries) {
            ExpenseEntry expenseEntry = convertToEntry(entry);
            expenseEntries.add(expenseEntry);
        }

        return expenseEntries;
    }

    private ExpenseEntry convertToEntry(Expense expense) throws EntityNotFoundException {

        ExpenseEntry expenseEntry = new ExpenseEntry();
        expenseEntry.setExpenseId(expense.getId());
        expenseEntry.setAmount(expense.getAmount());
        expenseEntry.setDescription(expense.getDescription());

        StudioEntry studioEntry = studioManager.getStudioById(expense.getStudio().getId());
        expenseEntry.setStudioId(studioEntry.getStudioId());

        expenseEntry.setExpenseDate(expense.getExpenseDate());
        expenseEntry.setExpenseCategory(ExpenseCategory.valueOf(expense.getExpenseCategory()));

        return expenseEntry;
    }

    private Expense convertToEntity(ExpenseEntry expenseEntry, Expense existingExpense) throws EntityNotFoundException {
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
        if (Objects.nonNull(expenseEntry.getStudioId())) {
            StudioEntry studioEntry = studioManager.getStudioById(expenseEntry.getStudioId());
            expense.setStudio(ConvertToEntryUtil.convertToEntity(studioEntry, null));
        }
        if (Objects.nonNull(expenseEntry.getExpenseDate())) {
            expense.setExpenseDate(expenseEntry.getExpenseDate());
        }
        if (Objects.nonNull(expenseEntry.getExpenseCategory())) {
            expense.setExpenseCategory(expenseEntry.getExpenseCategory().name());
        }

        return expense;
    }

    @Override
    public Long countExpensesByStudioId(Long studioId) {
        return expenseRepository.countExpensesByStudioId(studioId);
    }

    @Override
    public Long countExpensesByStudioIdAndMonth(Long studioId, Long startMonth, Long endMonth) {
        return expenseRepository.countExpensesByStudioIdAndMonthLong(studioId, startMonth, endMonth);
    }
}
