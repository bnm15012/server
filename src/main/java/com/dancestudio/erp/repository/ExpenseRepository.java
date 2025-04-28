package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Expense;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("SELECT s FROM Expense s WHERE s.branch.id = :branchId and month(s.expenseDate) >= :startMonth and month(s.expenseDate) <= :endMonth")
    Page<Expense> findAllByBranchId(@Param("branchId") Long branchId, @Param("startMonth") Long startMonth, @Param("endMonth") Long endMonth, Pageable pageable);

    @Query("SELECT s FROM Expense s WHERE s.branch.id = :branchId")
    Page<Expense> findExpensesByBranchId(@Param("branchId") Long branchId, Pageable pageable);

    @Query("SELECT COUNT(s) FROM Expense s WHERE s.branch.id = :branchId")
    Long countExpensesByBranchId(@Param("branchId") Long branchId);

    @Query("SELECT COUNT(s) FROM Expense s WHERE s.branch.id = :branchId and month(s.expenseDate) >= :startMonth and month(s.expenseDate) <= :endMonth")
    Long countExpensesByBranchIdAndMonthLong(@Param("branchId") Long branchId, @Param("startMonth") Long startMonth, @Param("endMonth") Long endMonth);

    @Query("SELECT MONTH(s.expenseDate), COUNT(s), SUM(s.amount) FROM Expense s " +
    "WHERE s.branch.id = :branchId AND " +
    "((YEAR(s.expenseDate) = YEAR(CURRENT_DATE) AND MONTH(s.expenseDate) = MONTH(CURRENT_DATE)) " +
    "OR (YEAR(s.expenseDate) = CASE WHEN MONTH(CURRENT_DATE) = 1 THEN YEAR(CURRENT_DATE) - 1 ELSE YEAR(CURRENT_DATE) END " +
    "AND MONTH(s.expenseDate) = CASE WHEN MONTH(CURRENT_DATE) = 1 THEN 12 ELSE MONTH(CURRENT_DATE) - 1 END)) " +
    "GROUP BY MONTH(s.expenseDate)")
    List<Object[]> countAndSumExpensesForCurrentAndLastMonth(@Param("branchId") Long branchId);

    @Query("SELECT e.expenseCategory, SUM(e.amount) FROM Expense e WHERE e.branch.id = :branchId AND MONTH(e.expenseDate) = :month AND YEAR(e.expenseDate) = :year GROUP BY e.expenseCategory")
    List<Object[]> findCategoryWiseSumOfExpensesByMonthAndYearAndBranchId(@Param("month") int month, @Param("year") int year, @Param("branchId") Long branchId);}
