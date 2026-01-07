package com.dancestudio.erp.modules.expense;

import com.dancestudio.erp.entry.PaymentExpenseSummary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

        @Query("""
               SELECT e FROM Expense e WHERE e.branch.id = :branchId AND e.expenseDate BETWEEN :startDate AND :endDate
               AND (:searchTerm IS NULL OR LOWER(e.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')))
        """)
        Page<Expense> findAllByBranchIdAndDateRange(@Param("branchId") Long branchId,@Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable, String searchTerm);

        @Query("""
               SELECT e FROM Expense e WHERE e.branch.id = :branchId AND (:searchTerm IS NULL OR LOWER(e.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')))
        """)
        Page<Expense> findByBranchId(Long branchId, Pageable pageable, String searchTerm);

        Long countByBranchId(Long branchId);

        @Query("""
               SELECT COUNT(e) FROM Expense e WHERE e.branch.id = :branchId AND e.expenseDate BETWEEN :startDate AND :endDate AND (:searchTerm IS NULL OR LOWER(e.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')))""")
        Long countByBranchIdAndDateRange(@Param("branchId") Long branchId, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("searchTerm") String searchTerm);

        @Query("""
              SELECT e.expenseCategory, SUM(e.amount) FROM Expense e WHERE e.branch.id = :branchId AND e.expenseDate BETWEEN :startDate AND :endDate GROUP BY e.expenseCategory
        """)
        List<Object[]> findCategoryWiseSumOfExpensesByDateRangeAndBranchId(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("branchId") Long branchId);

        @Query("""
               SELECT new com.dancestudio.erp.entry.PaymentExpenseSummary(COUNT(e), COALESCE(SUM(e.amount), 0))
               FROM Expense e WHERE e.branch.id = :branchId AND e.expenseDate BETWEEN :startDate AND :endDate
        """)
        PaymentExpenseSummary findCountAndTotalAmountByBranchAndDateRange(@Param("branchId") Long branchId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
