package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Payment;
import com.dancestudio.erp.entry.PaymentExpenseSummary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT s FROM Payment s WHERE s.branch.id = :branchId and year(s.paymentDate) >= :startYear and month(s.paymentDate) >= :startMonth and month(s.paymentDate) <= :endMonth and year(s.paymentDate) <= :endYear")
    List<Payment> findAllByBranchId(@Param("branchId") Long branchId, @Param("startMonth") Long startMonth, @Param("startYear") Long startYear, @Param("endMonth") Long endMonth, @Param("endYear") Long endYear);

    @Query("SELECT count(s) FROM Payment s WHERE s.branch.id = :branchId")
    Long getPaymentCountByBranchId(Long branchId);

    @Query("SELECT s FROM Payment s WHERE s.branch.id = :branchId")
    Page<Payment> findByBranchId(@Param("branchId") Long branchId, Pageable pageable);

    @Query("SELECT MONTH(s.paymentDate), COUNT(s), SUM(s.amount) FROM Payment s WHERE s.branch.id = :branchId AND " +
            "((YEAR(s.paymentDate) = YEAR(CURRENT_DATE) AND MONTH(s.paymentDate) = MONTH(CURRENT_DATE)) " +
            "OR (YEAR(s.paymentDate) = CASE WHEN MONTH(CURRENT_DATE) = 1 THEN YEAR(CURRENT_DATE) - 1 ELSE YEAR(CURRENT_DATE) END " +
            "AND MONTH(s.paymentDate) = CASE WHEN MONTH(CURRENT_DATE) = 1 THEN 12 ELSE MONTH(CURRENT_DATE) - 1 END)) GROUP BY MONTH(s.paymentDate)")
    List<Object[]> countAndSumPaymentsForCurrentAndLastMonth(@Param("branchId") Long branchId);

    @Query("SELECT p.payeeType, SUM(p.amount) FROM Payment p WHERE p.branch.id = :branchId AND MONTH(p.paymentDate) = :month AND YEAR(p.paymentDate) = :year GROUP BY p.payeeType")
    List<Object[]> findCategoryWiseSumOfPaymentsByMonthAndYearAndBranchId(@Param("month") int month, @Param("year") int year, @Param("branchId") Long branchId);

    @Query("SELECT p FROM Payment p WHERE p.payeeId = :payeeId and p.payeeType = :payeeType")
    Payment findByPayeeIdAndPayeeType(@Param("payeeId") Long payeeId, @Param("payeeType") String payeeType);

    @Query("SELECT new com.dancestudio.erp.entry.PaymentExpenseSummary(COUNT(s), COALESCE(SUM(s.amount), 0)) " +
    "FROM Payment s WHERE s.branch.id = :branchId AND s.paymentDate BETWEEN :startDate AND :endDate")
        PaymentExpenseSummary findCountAndTotalAmountByBranchAndDateRange(
        @Param("branchId") Long branchId,
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate);    
}
