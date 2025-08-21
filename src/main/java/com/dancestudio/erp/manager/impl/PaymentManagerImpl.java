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
import org.springframework.data.domain.Sort;
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
    public PaymentEntry getPaymentEntryByStudentActivityAssignmentId(Long studentActivityAssignmentId)
            throws Exception {
        Payment payment = paymentRepository.findByPayeeId(studentActivityAssignmentId);
        if (Objects.isNull(payment)) {
            throw new Exception("Payment not found");
        }
        return convertToEntry(payment);

    }

    @SneakyThrows
    @Override
    public List<PaymentEntry> getAllPaymentsByBranch(Long branchId, int page, int size, Integer startDate,
            Integer startMonth, Integer startYear, Integer endDate, Integer endMonth, Integer endYear, String status,
            String searchTerm) {
        Page<Payment> entries;
        Pageable pageable = size == -1 ? Pageable.unpaged()
                : PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "lastModifiedOn"));
        if (Objects.nonNull(startDate) && Objects.nonNull(startMonth) && Objects.nonNull(startYear)
                && Objects.nonNull(endDate) && Objects.nonNull(endMonth) && Objects.nonNull(endYear)) {
            Map<String, Date> monthRange = DateUtil.getUTCDateRange(startDate, startMonth, startYear, endDate,
                    endMonth, endYear);
            entries = paymentRepository.findAllByBranchIdAndPaymentDateBetweenAndOptionalStatus(branchId,
                    monthRange.get("start"), monthRange.get("end"), status, pageable);
        } else {
            List<Payment> payments = paymentRepository.findByBranchId(branchId, pageable).getContent();
            return payments.stream()
                    .map(this::convertToEntry)
                    .toList();
        }

        return entries.stream()
                .map(this::convertToEntry)
                .filter(paymentEntry -> {
                    if (searchTerm == null || searchTerm.isBlank()) {
                        return true;
                    }
                    String lowerSearchTerm = searchTerm.toLowerCase();
                    return (paymentEntry.getStudentEntry() != null && paymentEntry.getStudentEntry().getName() != null
                            &&
                            paymentEntry.getStudentEntry().getName().toLowerCase().contains(lowerSearchTerm)) ||
                            (paymentEntry.getStudentEntry() != null && paymentEntry.getStudentEntry().toString()
                                    .toLowerCase().contains(lowerSearchTerm));
                })
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
        paymentEntry.setActualAmount(payment.getActualAmount());
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
        if (Objects.nonNull(paymentEntry.getActualAmount())) {
            payment.setActualAmount(paymentEntry.getActualAmount());
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
    public Long getPaymentCountByStudioId(Long branchId, String searchTerm) {
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
