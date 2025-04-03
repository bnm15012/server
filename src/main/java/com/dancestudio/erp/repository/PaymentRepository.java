package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Payment;
import com.dancestudio.erp.entry.ReportEntry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT new com.dancestudio.erp.entry.ReportEntry(MONTH(p.paymentDate), SUM(p.amount)) FROM Payment p WHERE YEAR(p.paymentDate) = :year GROUP BY MONTH(p.paymentDate) ORDER BY MONTH(p.paymentDate)")
    List<ReportEntry> calculateTotalIncomeByYear(@Param("year") int year);

    @Query("SELECT s FROM Payment s WHERE s.studio.id = :studioId")
    List<Payment> findAllByStudioId(@Param("studioId") Long studioId);

    @Query("SELECT count(s) FROM Payment s WHERE s.studio.id = :studioId")
    Long getPaymentCountByStudioId(Long studioId);

    @Query("SELECT s FROM Payment s WHERE s.studio.id = :studioId")
    Page<Payment> findByStudioId(@Param("studioId") Long studioId, Pageable pageable);
    
    @Query("SELECT MONTH(s.paymentDate), COUNT(s), SUM(s.amount) FROM Payment s " +
    "WHERE s.studio.id = :studioId AND " +
    "((YEAR(s.paymentDate) = YEAR(CURRENT_DATE) AND MONTH(s.paymentDate) = MONTH(CURRENT_DATE)) " +
    "OR (YEAR(s.paymentDate) = CASE WHEN MONTH(CURRENT_DATE) = 1 THEN YEAR(CURRENT_DATE) - 1 ELSE YEAR(CURRENT_DATE) END " +
    "AND MONTH(s.paymentDate) = CASE WHEN MONTH(CURRENT_DATE) = 1 THEN 12 ELSE MONTH(CURRENT_DATE) - 1 END)) " +
    "GROUP BY MONTH(s.paymentDate)")
    List<Object[]> countAndSumPaymentsForCurrentAndLastMonth(@Param("studioId") Long studioId);

}
