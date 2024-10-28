package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.PaymentEntry;
import com.dancestudio.erp.manager.PaymentManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentManager paymentService;

    @PostMapping
    public PaymentEntry addPayment(@RequestBody PaymentEntry paymentEntry) {
        return paymentService.addPayment(paymentEntry);
    }

    @PutMapping("/{paymentId}")
    public PaymentEntry updatePayment(@PathVariable Long paymentId, @RequestBody PaymentEntry paymentEntry) {
        return paymentService.updatePayment(paymentId, paymentEntry);
    }

    @DeleteMapping("/{paymentId}")
    public void deletePayment(@PathVariable Long paymentId) {
        paymentService.deletePayment(paymentId);
    }

    @GetMapping("/{paymentId}")
    public PaymentEntry getPaymentById(@PathVariable Long paymentId) {
        return paymentService.getPaymentById(paymentId);
    }

    @GetMapping
    public List<PaymentEntry> getAllPayments() {
        return paymentService.getAllPayments();
    }
}
