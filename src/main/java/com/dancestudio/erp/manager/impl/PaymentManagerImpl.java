package com.dancestudio.erp.manager.impl;


import com.dancestudio.erp.entry.PaymentEntry;
import com.dancestudio.erp.entity.Payment;
import com.dancestudio.erp.entry.ReportEntry;
import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.repository.PaymentRepository;
import com.dancestudio.erp.manager.PaymentManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentManagerImpl implements PaymentManager {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentManagerImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentEntry addPayment(PaymentEntry paymentEntry) {
        Payment payment = convertToEntity(paymentEntry);
        return convertToEntry(paymentRepository.save(payment));
    }

    @Override
    public PaymentEntry updatePaymentStatus(Long paymentId, String status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(PaymentStatus.valueOf(status));
        return convertToEntry(paymentRepository.save(payment));
    }

    @Override
    public PaymentEntry getPaymentById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        return convertToEntry(payment);
    }

    @Override
    public PaymentEntry updatePayment(Long paymentId, PaymentEntry paymentEntry) {
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
    public List<PaymentEntry> getAllPayments() {
        List<Payment> entries = paymentRepository.findAll().stream().collect(Collectors.toList());

        List<PaymentEntry> paymentEntries = new ArrayList<>();
        for (Payment entry : entries) {
            PaymentEntry paymentEntry = convertToEntry(entry);
            paymentEntries.add(paymentEntry);
        }

        return paymentEntries;
    }

    @Override
    public List<ReportEntry> calculateTotalIncome(Long year) {
       return paymentRepository.calculateTotalIncomeByYear(Math.toIntExact(year));
    }

    private PaymentEntry convertToEntry(Payment payment) {

        PaymentEntry paymentEntry = new PaymentEntry();
        paymentEntry.setPaymentId(payment.getId());
        paymentEntry.setPayeeId(payment.getPayeeId());
        paymentEntry.setAmount(payment.getAmount());
        paymentEntry.setPaymentDate(payment.getPaymentDate());
        paymentEntry.setStatus(payment.getStatus());
        paymentEntry.setPaymentType(payment.getPaymentType());

        return paymentEntry;
    }

    private Payment convertToEntity(PaymentEntry paymentEntry) {

        Payment payment = new Payment();
        payment.setId(paymentEntry.getPaymentId());
        payment.setPayeeId(paymentEntry.getPayeeId());
        payment.setAmount(paymentEntry.getAmount());
        payment.setPaymentDate(paymentEntry.getPaymentDate());
        payment.setStatus(paymentEntry.getStatus());
        payment.setPaymentType(paymentEntry.getPaymentType());

        return payment;
    }
}
