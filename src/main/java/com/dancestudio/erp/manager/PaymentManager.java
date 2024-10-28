package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.PaymentEntry;
import com.dancestudio.erp.entry.ReportEntry;

import java.util.List;

public interface PaymentManager {

    PaymentEntry addPayment(PaymentEntry paymentEntry);

    PaymentEntry updatePaymentStatus(Long paymentId, String status);

    PaymentEntry updatePayment(Long paymentId, PaymentEntry paymentEntry);

    void deletePayment(Long paymentId);

    PaymentEntry getPaymentById(Long paymentId);

    List<PaymentEntry> getAllPayments();

    List<ReportEntry> calculateTotalIncome(Long year);
}
