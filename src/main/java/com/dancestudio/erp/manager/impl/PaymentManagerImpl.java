package com.dancestudio.erp.manager.impl;

import org.springframework.beans.factory.annotation.Value;
import com.dancestudio.erp.entity.Payment;
import com.dancestudio.erp.entry.PaymentEntry;
import com.dancestudio.erp.entry.ReportEntry;
import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.enums.PaymentType;
import com.dancestudio.erp.manager.PaymentManager;
import com.dancestudio.erp.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

import lombok.extern.slf4j.Slf4j;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PaymentManagerImpl implements PaymentManager {

    private final PaymentRepository paymentRepository;

    @Value("${razorpay.api_secret}")
    private String razorpaySecret;
    
    @Autowired
    private RazorpayClient razorpayClient;

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
    public List<PaymentEntry> getAllPaymentsByStudio(Long studioId) {
        List<Payment> entries = paymentRepository.findAllByStudioId(studioId);

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
        paymentEntry.setStatus(PaymentStatus.valueOf(payment.getStatus()));
        paymentEntry.setPaymentType(PaymentType.valueOf(payment.getPaymentType()));

        return paymentEntry;
    }

    private Payment convertToEntity(PaymentEntry paymentEntry) {

        Payment payment = new Payment();
        payment.setId(paymentEntry.getPaymentId());
        payment.setPayeeId(paymentEntry.getPayeeId());
        payment.setAmount(paymentEntry.getAmount());
        payment.setPaymentDate(paymentEntry.getPaymentDate());
        payment.setStatus(paymentEntry.getStatus().name());
        payment.setPaymentType(paymentEntry.getPaymentType().name());

        return payment;
    }

    @Override
    public PaymentEntry createOrder(PaymentEntry paymentEntry) {
        try {
            PaymentEntry entry = new PaymentEntry();

            JSONObject options = new JSONObject();
            options.put("amount", paymentEntry.getAmount() * 100);
            options.put("currency", "INR");
            options.put("receipt", "receipt#1");

            Order order = razorpayClient.Orders.create(options);

            entry.setMessage(order.toString());
            return entry;
        } catch (Exception e) {
            log.error("Error creating order", e);
            throw new RuntimeException("Order not created");
        }
    }

    @Override
    public PaymentEntry verifyPayment(String orderId, String paymentId, String signature) throws Exception {
        try {
            PaymentEntry entry = new PaymentEntry();

            boolean isVerified = verifySignature(orderId, paymentId, signature);
            log.info("Order ID: {}, Payment ID: {}, Signature: {}", orderId, paymentId, signature);

            if (isVerified) {
                entry.setMessage("Payment verified successfully!");
                return entry;
            } else {
                entry.setMessage("Payment verification failed: Invalid signature");
                return entry;
            }
        } catch (Exception e) {
            log.error("Payment verification failed: {}", e.getMessage(), e);
            throw new Exception("Payment verification failed: " + e.getMessage(), e);
        }
    }

    private boolean verifySignature(String orderId, String paymentId, String providedSignature) {
        try {
            JSONObject options = new JSONObject();
            options.put("razorpay_order_id", orderId);
            options.put("razorpay_payment_id", paymentId);
            options.put("razorpay_signature", providedSignature);

            return com.razorpay.Utils.verifyPaymentSignature(options, razorpaySecret);
        } catch (Exception e) {
            log.error("Error verifying signature: ", e);
            return false;
        }
    }

}
