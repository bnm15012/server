package com.dancestudio.erp.modules.expense;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.dancestudio.erp.entity.Branch;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.repository.BranchRepository;
import jakarta.annotation.PostConstruct;

@Component
public class ExpenseConvertor {

    private static ApplicationContext applicationContext;

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        applicationContext = context;
    }

    public static ExpenseEntry toEntry(Expense expense) {

        ExpenseEntry expenseEntry = new ExpenseEntry();
        expenseEntry.setExpenseId(expense.getId());
        expenseEntry.setAmount(expense.getAmount());
        expenseEntry.setDescription(expense.getDescription());
        expenseEntry.setBranchId(expense.getBranch().getId());
        expenseEntry.setExpenseDate(expense.getExpenseDate());
        expenseEntry.setExpenseCategory(ExpenseCategory.valueOf(expense.getExpenseCategory()));

        return expenseEntry;
    }

    public static Expense toEntity(ExpenseEntry expenseEntry, Expense existingExpense) throws EntityNotFoundException{
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
            BranchRepository branchRepository = applicationContext.getBean(BranchRepository.class);
            Branch branch = branchRepository.findById(expenseEntry.getBranchId())
                    .orElseThrow(() -> new EntityNotFoundException("Branch not found"));

            expense.setBranch(branch);
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
