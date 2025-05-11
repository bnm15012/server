package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.PaymentEntry;
import com.dancestudio.erp.enums.PayeeType;
import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;

import java.util.List;

public interface PaymentManager extends BaseManager<PaymentEntry, Long> {

    PaymentEntry updatePaymentStatus(Long paymentId, PaymentStatus status) throws EntityNotFoundException;

    List<PaymentEntry> getAllPaymentsByBranch(Long branchId, int page, int size, Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, String status, String searchTerm);

    Long getPaymentCountByStudioId(Long studioId);

    PaymentEntry getPaymentByPayeeIdAndPayeeType(Long bookingId, PayeeType payeeType) throws EntityNotFoundException;

    PaymentEntry getPaymentEntryByStudentActivityAssignmentId(Long studentActivityAssignmentId) throws Exception;

}
