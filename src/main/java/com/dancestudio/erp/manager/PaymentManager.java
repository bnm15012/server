package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.PaymentEntry;
import com.dancestudio.erp.entry.ReportEntry;
import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;

import java.util.List;

public interface PaymentManager {

    PaymentEntry addPayment(PaymentEntry paymentEntry) throws Exception;

    PaymentEntry updatePaymentStatus(Long paymentId, PaymentStatus status) throws EntityNotFoundException;

    PaymentEntry updatePayment(Long paymentId, PaymentEntry paymentEntry) throws Exception;

    void deletePayment(Long paymentId);

    PaymentEntry getPaymentById(Long paymentId) throws EntityNotFoundException;

    List<PaymentEntry> getAllPaymentsByStudio(Long studioId, int size, int limit);

    List<ReportEntry> calculateTotalIncome(Long year);

    Long getPaymentCountByStudioId(Long studioId);
}
