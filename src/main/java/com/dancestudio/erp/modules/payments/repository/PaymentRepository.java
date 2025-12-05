package com.dancestudio.erp.modules.payments.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.dancestudio.erp.modules.payments.entity.Payment;

public interface PaymentRepository
                extends JpaRepository<Payment, Long>, JpaSpecificationExecutor<Payment> {

        @EntityGraph(attributePaths = {
                        "paymentBooking.booking.client",
                        "paymentStudentActivity.studentActivityAssignment.student"
        })
        Page<Payment> findAll(Specification<Payment> spec, Pageable pageable);
}
