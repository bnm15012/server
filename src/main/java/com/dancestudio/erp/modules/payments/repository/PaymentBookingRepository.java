package com.dancestudio.erp.modules.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dancestudio.erp.modules.payments.entity.PaymentBooking;

public interface PaymentBookingRepository extends JpaRepository<PaymentBooking, Long> {

    
}
