package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.Payment;
import com.dancestudio.erp.entity.StudentActivityAssignment;
import com.dancestudio.erp.entry.BranchEntry;
import com.dancestudio.erp.entry.PaymentEntry;
import com.dancestudio.erp.enums.PayeeType;
import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.enums.PaymentType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.BranchManager;
import com.dancestudio.erp.manager.ClientManager;
import com.dancestudio.erp.manager.InstructorManager;
import com.dancestudio.erp.manager.PaymentManager;
import com.dancestudio.erp.repository.PaymentRepository;
import com.dancestudio.erp.repository.StudentActivityAssignmentRepository;
import com.dancestudio.erp.util.ConvertToEntryUtil;
import com.dancestudio.erp.util.DateUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PaymentManagerImpl implements PaymentManager {

    private final PaymentRepository paymentRepository;
    @Autowired
    private BranchManager branchManager;
    @Autowired
    private StudentActivityAssignmentRepository studentActivityAssignmentRepository;
    @Autowired
    private ClientManager clientManager;
    @Autowired
    private InstructorManager instructorManager;

    @Autowired
    public PaymentManagerImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentEntry add(PaymentEntry paymentEntry) throws Exception {
        Payment payment = convertToEntity(paymentEntry, null);
        return convertToEntry(paymentRepository.save(payment));
    }

    @Override
    public PaymentEntry updatePaymentStatus(Long paymentId, PaymentStatus status) throws EntityNotFoundException {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(status.name());
        return convertToEntry(paymentRepository.save(payment));
    }

    @Override
    public PaymentEntry getById(Long paymentId) throws EntityNotFoundException {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        return convertToEntry(payment);
    }

    @Override
    public PaymentEntry update(Long paymentId, PaymentEntry paymentEntry) throws Exception {
        Payment existingPayment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        Payment newPaymentEntry = convertToEntity(paymentEntry, existingPayment);
        return convertToEntry(paymentRepository.save(newPaymentEntry));
    }

    @Override
    public void delete(Long paymentId) throws EntityNotFoundException {
        paymentRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment Id not found"));

        paymentRepository.deleteById(paymentId);
    }

    @Override
    public PaymentEntry getPaymentEntryByStudentActivityAssignmentId(Long studentActivityAssignmentId) throws Exception {
        Payment payment = paymentRepository.findByPayeeId(studentActivityAssignmentId);
        if(Objects.isNull(payment)) {
            throw new Exception("Payment not found");
        }
        return convertToEntry(payment);

     }

    @SneakyThrows
    @Override
    public List<PaymentEntry> getAllPaymentsByBranch(Long branchId, int page, int size, Integer startMonth,
            Integer startYear, Integer endMonth, Integer endYear, String status) {
        Page<Payment> entries;
        Pageable pageable = size == -1 ? Pageable.unpaged() : PageRequest.of(page, size);
        if (size == 7) {
            entries = paymentRepository.findByBranchId(branchId, pageable);
        } else {
            Map<String, Date> monthRange = DateUtil.getDateRangeByMonthYear(startMonth, startYear, endMonth, endYear);
            entries = paymentRepository.findAllByBranchIdAndPaymentDateBetweenAndOptionalStatus(branchId,
                    monthRange.get("start"),
                    monthRange.get("end"), status, pageable);
        }
        return entries.stream()
                .map(this::convertToEntry)
                .collect(Collectors.toList());
    }

    private PaymentEntry convertToEntry(Payment payment) {
        PaymentEntry paymentEntry = new PaymentEntry();
        paymentEntry.setPaymentId(String.valueOf(payment.getId()));
        paymentEntry.setPayeeId(payment.getPayeeId());

        try {
            if (PayeeType.STUDENT.name().equals(payment.getPayeeType())) {
                Optional<StudentActivityAssignment> studentActivityAssignmentOptional = studentActivityAssignmentRepository
                        .findById(payment.getPayeeId());
                studentActivityAssignmentOptional.ifPresent(studentActivityAssignment -> paymentEntry
                        .setStudentEntry(ConvertToEntryUtil.convertToEntry(studentActivityAssignment.getStudent())));
            } else if (PayeeType.BOOKING.name().equals(payment.getPayeeType())) {
                paymentEntry.setClientEntry(clientManager.getById(payment.getPayeeId()));
            }
        } catch (Exception ex) {
            log.error("Error converting payment entry: {}", ex.getMessage());
        }

        paymentEntry.setPayeeId(payment.getPayeeId());
        paymentEntry.setPayeeType(PayeeType.valueOf(payment.getPayeeType()));
        paymentEntry.setAmount(payment.getAmount());
        paymentEntry.setPaymentDate(payment.getPaymentDate());
        paymentEntry.setStatus(PaymentStatus.valueOf(payment.getStatus()));
        paymentEntry.setPaymentType(PaymentType.valueOf(payment.getPaymentType()));
        paymentEntry.setBranchId(payment.getBranch().getId());
        return paymentEntry;
    }

    private Payment convertToEntity(PaymentEntry paymentEntry, Payment existingPayment) throws Exception {

        Payment payment = (existingPayment != null) ? existingPayment : new Payment();

        if (Objects.nonNull(paymentEntry.getPaymentId())) {
            payment.setId(Long.valueOf(paymentEntry.getPaymentId()));
        }

        if (Objects.nonNull(paymentEntry.getStudentEntry())
                && Objects.nonNull(paymentEntry.getStudentEntry().getStudentId())) {
            payment.setPayeeId(paymentEntry.getStudentEntry().getStudentId());
        } else if (Objects.nonNull(paymentEntry.getClientEntry())
                && Objects.nonNull(paymentEntry.getClientEntry().getClientId())) {
            payment.setPayeeId(paymentEntry.getClientEntry().getClientId());
        }

        if (Objects.nonNull(paymentEntry.getPayeeId())) {
            payment.setPayeeId(paymentEntry.getPayeeId());
        }
        if (Objects.nonNull(paymentEntry.getAmount())) {
            payment.setAmount(paymentEntry.getAmount());
        }
        if (Objects.nonNull(paymentEntry.getPayeeType())) {
            payment.setPayeeType(paymentEntry.getPayeeType().name());
        }
        if (Objects.nonNull(paymentEntry.getPaymentDate())) {
            payment.setPaymentDate(paymentEntry.getPaymentDate());
        }
        if (Objects.nonNull(paymentEntry.getStatus())) {
            payment.setStatus(paymentEntry.getStatus().name());
        }
        if (Objects.nonNull(paymentEntry.getPaymentType())) {
            payment.setPaymentType(paymentEntry.getPaymentType().name());
        }
        if (Objects.nonNull(paymentEntry.getBranchId())) {
            BranchEntry entry = branchManager.getById(paymentEntry.getBranchId());
            payment.setBranch(ConvertToEntryUtil.convertToEntity(entry, null));
        }

        return payment;
    }

    @Override
    public Long getPaymentCountByStudioId(Long branchId) {
        return paymentRepository.getPaymentCountByBranchId(branchId);
    }

    @Override
    public PaymentEntry getPaymentByPayeeIdAndPayeeType(Long bookingId, PayeeType payeeType)
            throws EntityNotFoundException {
        Payment payment = paymentRepository.findByPayeeIdAndPayeeType(bookingId, payeeType.toString());
        if (payment == null) {
            throw new EntityNotFoundException("Payment not found");
        }
        return convertToEntry(payment);
    }
}
