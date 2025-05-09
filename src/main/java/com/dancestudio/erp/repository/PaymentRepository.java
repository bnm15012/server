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

    @Query("SELECT p FROM Payment p WHERE p.payeeId = :payeeId")
    Payment findByPayeeId(@Param("payeeId") Long payeeId);

      @Query("""
          SELECT e FROM Payment e 
           WHERE e.branch.id = :branchId 
             AND e.paymentDate BETWEEN :startDate AND :endDate 
             AND (:status IS NULL OR e.status = :status)
      """)
      Page<Payment> findAllByBranchIdAndPaymentDateBetweenAndOptionalStatus(
          @Param("branchId") Long branchId,
          @Param("startDate") Date startDate,
          @Param("endDate") Date endDate,
          @Param("status") String status,
          Pageable pageable
      );

    @Query("SELECT COUNT(s) FROM Payment s WHERE s.branch.id = :branchId")
    Long getPaymentCountByBranchId(@Param("branchId") Long branchId);

    @Query("SELECT s FROM Payment s WHERE s.branch.id = :branchId")
    Page<Payment> findByBranchId(@Param("branchId") Long branchId, Pageable pageable);

    @Query("""
        SELECT s.paymentDate, COUNT(s), SUM(s.amount) 
          FROM Payment s 
         WHERE s.branch.id = :branchId 
           AND s.paymentDate BETWEEN :startDate AND :endDate
         GROUP BY s.paymentDate
         ORDER BY s.paymentDate
    """)
    Page<Object[]> countAndSumPaymentsByDateRange(
        @Param("branchId") Long branchId,
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate,
        Pageable pageable
    );

    @Query("""
        SELECT p.payeeType, SUM(p.amount) 
          FROM Payment p 
         WHERE p.branch.id = :branchId 
           AND p.paymentDate BETWEEN :startDate AND :endDate 
         GROUP BY p.payeeType
    """)
    List<Object[]> findCategoryWiseSumOfPaymentsByDateRange(
        @Param("branchId") Long branchId,
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate
    );

    @Query("SELECT p FROM Payment p WHERE p.payeeId = :payeeId AND p.payeeType = :payeeType")
    Payment findByPayeeIdAndPayeeType(
        @Param("payeeId") Long payeeId,
        @Param("payeeType") String payeeType
    );

    @Query("""
        SELECT new com.dancestudio.erp.entry.PaymentExpenseSummary(COUNT(s), COALESCE(SUM(s.amount), 0)) 
          FROM Payment s 
         WHERE s.branch.id = :branchId 
           AND s.status = 'COMPLETED' 
           AND s.paymentDate BETWEEN :startDate AND :endDate
    """)
    PaymentExpenseSummary findCountAndTotalAmountByBranchAndDateRange(
        @Param("branchId") Long branchId,
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate
    );
}
