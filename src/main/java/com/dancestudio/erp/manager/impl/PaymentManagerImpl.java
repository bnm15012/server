package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.StudioManager;
import com.dancestudio.erp.util.ConvertToEntryUtil;

import com.dancestudio.erp.entity.Payment;
import com.dancestudio.erp.entry.PaymentEntry;
import com.dancestudio.erp.entry.ReportEntry;
import com.dancestudio.erp.enums.PayeeType;
import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.enums.PaymentType;
import com.dancestudio.erp.manager.PaymentManager;
import com.dancestudio.erp.repository.PaymentRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PaymentManagerImpl implements PaymentManager {

    private final PaymentRepository paymentRepository;
    @Autowired
    private StudioManager studioManager;

    @Autowired
    public PaymentManagerImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentEntry addPayment(PaymentEntry paymentEntry) throws Exception {
        Payment payment = convertToEntity(paymentEntry);
        return convertToEntry(paymentRepository.save(payment));
    }

    @Override
    public PaymentEntry updatePaymentStatus(Long paymentId, PaymentStatus status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(status.name());
        return convertToEntry(paymentRepository.save(payment));
    }

    @Override
    public PaymentEntry getPaymentById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        return convertToEntry(payment);
    }

    @Override
    public PaymentEntry updatePayment(Long paymentId, PaymentEntry paymentEntry) throws Exception {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        Payment newPaymentEntry = convertToEntity(paymentEntry);
        return convertToEntry(paymentRepository.save(newPaymentEntry));
    }

    @Override
    public void deletePayment(Long paymentId) {
        paymentRepository.deleteById(paymentId);
    }
    
    @Override
    public List<PaymentEntry> getAllPaymentsByStudio(Long studioId, int page, int size) {
        if (size == -1) {
            List<Payment> entries = paymentRepository.findAllByStudioId(studioId);
            return entries.stream()
                    .map(this::convertToEntry)
                    .collect(Collectors.toList());
        } else {
            Pageable pageable = PageRequest.of(page, size);
            Page<Payment> paymentPage = paymentRepository.findByStudioId(studioId, pageable);
            return paymentPage.getContent().stream()
                    .map(this::convertToEntry)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<ReportEntry> calculateTotalIncome(Long year) {
        return paymentRepository.calculateTotalIncomeByYear(Math.toIntExact(year));
    }

    private PaymentEntry convertToEntry(Payment payment) {

        PaymentEntry paymentEntry = new PaymentEntry();
        paymentEntry.setPaymentId(String.valueOf(payment.getId()));
        paymentEntry.setPayeeId(payment.getPayeeId());
        paymentEntry.setPayeeType(PayeeType.valueOf(payment.getPayeeType()));
        paymentEntry.setAmount(payment.getAmount());
        paymentEntry.setPaymentDate(payment.getPaymentDate());
        paymentEntry.setStatus(PaymentStatus.valueOf(payment.getStatus()));
        paymentEntry.setPaymentType(PaymentType.valueOf(payment.getPaymentType()));
        paymentEntry.setStudioId(payment.getStudio().getId());
        return paymentEntry;
    }

   private Payment convertToEntity(PaymentEntry paymentEntry) throws EntityNotFoundException {
 
         Payment payment = new Payment();
 
         if(Objects.nonNull(paymentEntry.getPaymentId())) {
             payment.setId(Long.valueOf(paymentEntry.getPaymentId()));
         }
         if(Objects.nonNull(paymentEntry.getPayeeId())) {
             payment.setPayeeId(paymentEntry.getPayeeId());
         }
         if(Objects.nonNull(paymentEntry.getAmount())) {
             payment.setAmount(paymentEntry.getAmount());
         }
         if(Objects.nonNull(paymentEntry.getPayeeType())) {
             payment.setPayeeType(paymentEntry.getPayeeType().name());
         }
         if(Objects.nonNull(paymentEntry.getPaymentDate())) {
             payment.setPaymentDate(paymentEntry.getPaymentDate());
         }
         if(Objects.nonNull(paymentEntry.getStatus())) {
             payment.setStatus(paymentEntry.getStatus().name());
         }
         if(Objects.nonNull(paymentEntry.getPaymentType())) {
             payment.setPaymentType(paymentEntry.getPaymentType().name());
         }
         if (Objects.nonNull(paymentEntry.getStudioId())) {
             StudioEntry entry = studioManager.getStudioById(paymentEntry.getStudioId());
             payment.setStudio(ConvertToEntryUtil.convertToEntity(entry, null));
         }
 
         return payment;
     }

    @Override
    public Long getPaymentCountByStudioId(Long studioId) {
        return paymentRepository.getPaymentCountByStudioId(studioId);
    }
}
