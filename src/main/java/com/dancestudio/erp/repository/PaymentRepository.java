package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Payment;
import com.dancestudio.erp.entry.ReportEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT new com.dancestudio.erp.entry.ReportEntry(MONTH(p.paymentDate), SUM(p.amount)) FROM Payment p WHERE YEAR(p.paymentDate) = :year GROUP BY MONTH(p.paymentDate) ORDER BY MONTH(p.paymentDate)")
    List<ReportEntry> calculateTotalIncomeByYear(@Param("year") int year);

    @Query("SELECT s FROM Payment s WHERE s.studioId = :studioId")
    List<Payment> findAllByStudioId(@Param("studioId") Long studioId);
}
