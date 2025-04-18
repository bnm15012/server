package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.PaymentEntry;
import com.dancestudio.erp.entry.ReportEntry;
import com.dancestudio.erp.enums.PayeeType;
import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;

import java.util.List;

public interface PaymentManager extends BaseManager<PaymentEntry, Long> {

    PaymentEntry updatePaymentStatus(Long paymentId, PaymentStatus status) throws EntityNotFoundException;

    List<PaymentEntry> getAllPaymentsByStudio(Long studioId, int size, int limit);

    List<ReportEntry> calculateTotalIncome(Long year);

    Long getPaymentCountByStudioId(Long studioId);

    PaymentEntry getPaymentByPayeeIdAndPayeeType(Long bookingId, PayeeType payeeType) throws EntityNotFoundException;
}
