package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.PaymentEntry;
import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.response.PaymentResponse;
import org.springframework.http.ResponseEntity;

public interface PaymentService extends BaseService<PaymentEntry, PaymentResponse, Long> {

    ResponseEntity<PaymentResponse> updatePaymentStatus(Long paymentId, PaymentStatus status);

    ResponseEntity<PaymentResponse> getAllPayments(Long studioId, int page, int size);
}
