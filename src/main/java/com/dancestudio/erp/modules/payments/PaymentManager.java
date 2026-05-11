package com.dancestudio.erp.modules.payments;

import com.dancestudio.erp.modules.payments.repository.PaymentStudentActivityRepository;
import com.dancestudio.erp.modules.payments.spec.PaymentSpecification;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.dancestudio.erp.enums.PayeeType;
import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.modules.payments.entity.Payment;
import com.dancestudio.erp.modules.payments.entity.PaymentBooking;
import com.dancestudio.erp.modules.payments.entity.PaymentStudentActivity;
import com.dancestudio.erp.modules.payments.entry.PaymentEntry;
import com.dancestudio.erp.modules.payments.enums.TransactionType;
import com.dancestudio.erp.modules.payments.repository.PaymentBookingRepository;
import com.dancestudio.erp.modules.payments.repository.PaymentRepository;

@Slf4j
@Service
public class PaymentManager {

    private final PaymentRepository paymentRepository;
    private final PaymentStudentActivityRepository paymentStudentActivityRepository;

    private final PaymentBookingRepository paymentBookingRepository;

    public PaymentManager(
            PaymentRepository paymentRepository,
            PaymentBookingRepository paymentBookingRepository,
            PaymentStudentActivityRepository paymentStudentActivityRepository) {
        this.paymentRepository = paymentRepository;
        this.paymentBookingRepository = paymentBookingRepository;
        this.paymentStudentActivityRepository = paymentStudentActivityRepository;
    }

    public PaymentEntry add(PaymentEntry paymentEntry) throws IllegalAccessException {
        if (Objects.isNull(paymentEntry.getTransactionType())) {
            paymentEntry.setTransactionType(TransactionType.CREDIT);
        }
        if (paymentEntry.getPayeeType().equals(PayeeType.BOOKING)) {
            PaymentBooking paymentBooking = PaymentConvertor.convertToEntity(paymentEntry, (PaymentBooking) null);
            return PaymentConvertor.convertToEntry(paymentBookingRepository.save(paymentBooking));
        } else if (paymentEntry.getPayeeType().equals(PayeeType.STUDENT)) {
            PaymentStudentActivity paymentStudentActivityAssignment = PaymentConvertor.convertToEntity(paymentEntry,
                    (PaymentStudentActivity) null);
            return PaymentConvertor
                    .convertToEntry(paymentStudentActivityRepository.save(paymentStudentActivityAssignment));
        }
        throw new IllegalAccessException("Payee type not found");
    }

    public PaymentEntry update(Long id, PaymentEntry paymentEntry)
            throws IllegalArgumentException, EntityNotFoundException {
        if (paymentEntry.getPayeeType().equals(PayeeType.BOOKING)) {
            PaymentBooking oldPaymentBooking = paymentBookingRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("No Payment found"));
            PaymentBooking paymentBooking = PaymentConvertor.convertToEntity(paymentEntry, oldPaymentBooking);
            return PaymentConvertor.convertToEntry(paymentBookingRepository.save(paymentBooking));
        } else if (paymentEntry.getPayeeType().equals(PayeeType.STUDENT)) {
            PaymentStudentActivity olPaymentStudentActivity = paymentStudentActivityRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("No Payment found"));
            PaymentStudentActivity paymentStudentActivityAssignment = PaymentConvertor.convertToEntity(paymentEntry,
                    olPaymentStudentActivity);
            return PaymentConvertor
                    .convertToEntry(paymentStudentActivityRepository.save(paymentStudentActivityAssignment));
        }
        throw new IllegalArgumentException("Payee type not found");
    }

    public Page<PaymentEntry> getAll(Long branchId, int page, int size, Date startDate, Date endDate,
            PaymentStatus status) {
        Pageable pageable = size == -1 ? Pageable.unpaged() : PageRequest.of(page, size, Sort.by("id").descending());
        Specification<Payment> spec = Specification.where(PaymentSpecification.withJoins())
                .and(PaymentSpecification.byBranch(branchId))
                .and(PaymentSpecification.byStatus(status))
                .and(PaymentSpecification.byDateRange(startDate, endDate));

        Page<Payment> pages = paymentRepository.findAll(spec, pageable);

        return pages.map(PaymentConvertor::convertToEntry);
    }

}
