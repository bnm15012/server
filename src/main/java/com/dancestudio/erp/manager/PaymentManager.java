package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.PaymentEntry;
import com.dancestudio.erp.entry.ReportEntry;
import com.dancestudio.erp.enums.PaymentStatus;

import java.util.List;

public interface PaymentManager {

    PaymentEntry addPayment(PaymentEntry paymentEntry);

    PaymentEntry updatePaymentStatus(Long paymentId, PaymentStatus status);

    PaymentEntry updatePayment(Long paymentId, PaymentEntry paymentEntry);

    void deletePayment(Long paymentId);

    PaymentEntry getPaymentById(Long paymentId);

    List<PaymentEntry> getAllPaymentsByStudio(Long studioId);

    List<ReportEntry> calculateTotalIncome(Long year);
}
