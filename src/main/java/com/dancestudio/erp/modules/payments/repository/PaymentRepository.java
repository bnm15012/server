package com.dancestudio.erp.modules.payments.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dancestudio.erp.entry.PaymentExpenseSummary;
import com.dancestudio.erp.modules.payments.entity.Payment;

public interface PaymentRepository
    extends JpaRepository<Payment, Long>, JpaSpecificationExecutor<Payment> {

  @EntityGraph(attributePaths = {
      "paymentBooking.booking.client",
      "paymentStudentActivity.studentActivityAssignment.student"
  })
  Page<Payment> findAll(Specification<Payment> spec, Pageable pageable);

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
      @Param("endDate") Date endDate);
}
