package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.PaymentEntry;
import com.dancestudio.erp.enums.PayeeType;
import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;

import java.util.List;

public interface PaymentManager extends BaseManager<PaymentEntry, Long> {

    PaymentEntry updatePaymentStatus(Long paymentId, PaymentStatus status) throws EntityNotFoundException;

    List<PaymentEntry> getAllPaymentsByBranch(Long branchId, int size, int limit, Long startMonth, Long startYear, Long endMonth, Long endYear);

    Long getPaymentCountByStudioId(Long studioId);

    PaymentEntry getPaymentByPayeeIdAndPayeeType(Long bookingId, PayeeType payeeType) throws EntityNotFoundException;

    List<PaymentEntry> getAllPaymentsByDateRange(Long branchId, int startMonth, int startYear, int endMonth, int endYear) throws Exception;
}
