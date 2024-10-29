package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.PaymentEntry;
import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.response.PaymentResponse;

public interface PaymentService {

    PaymentResponse addPayment(PaymentEntry paymentEntry);

    PaymentResponse updatePaymentStatus(Long paymentId, PaymentStatus status);

    PaymentResponse updatePayment(Long paymentId, PaymentEntry paymentEntry);

    void deletePayment(Long paymentId);

    PaymentResponse getPaymentById(Long paymentId);

    PaymentResponse getAllPayments(Long studioId);
}
