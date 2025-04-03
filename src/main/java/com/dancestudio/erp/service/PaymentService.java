package com.dancestudio.erp.service;

import org.springframework.http.ResponseEntity;

import com.dancestudio.erp.entry.PaymentEntry;
import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.response.PaymentResponse;

public interface PaymentService {

    ResponseEntity<PaymentResponse> addPayment(PaymentEntry paymentEntry);

    ResponseEntity<PaymentResponse> updatePaymentStatus(Long paymentId, PaymentStatus status);

    ResponseEntity<PaymentResponse> updatePayment(Long paymentId, PaymentEntry paymentEntry);

    void deletePayment(Long paymentId);

    ResponseEntity<PaymentResponse> getPaymentById(Long paymentId);

    ResponseEntity<PaymentResponse> getAllPayments(Long studioId, int page, int size);
}
