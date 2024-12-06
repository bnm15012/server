package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.PaymentEntry;
import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.response.PaymentResponse;
import com.dancestudio.erp.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/add")
    public PaymentResponse addPayment(@RequestBody PaymentEntry paymentEntry) {
        return paymentService.addPayment(paymentEntry);
    }

    @PutMapping("/update/{paymentId}")
    public PaymentResponse updatePayment(@PathVariable Long paymentId, @RequestBody PaymentEntry paymentEntry) {
        return paymentService.updatePayment(paymentId, paymentEntry);
    }

    @PutMapping("/updateStatus/{paymentId}")
    public PaymentResponse updatePaymentStatus(@PathVariable Long paymentId, @PathVariable PaymentStatus status) {
        return paymentService.updatePaymentStatus(paymentId, status);
    }

    @DeleteMapping("/delete/{paymentId}")
    public void deletePayment(@PathVariable Long paymentId) {
        paymentService.deletePayment(paymentId);
    }

    @GetMapping("/get/{paymentId}")
    public PaymentResponse getPaymentById(@PathVariable Long paymentId) {
        return paymentService.getPaymentById(paymentId);
    }

    @GetMapping("/getAllPayments/{studioId}")
    public PaymentResponse getAllPayments(@PathVariable Long studioId) {
        return paymentService.getAllPayments(studioId);
    }

    @PostMapping("/createOrder")
    public ResponseEntity<PaymentResponse> createOrder(@RequestBody PaymentEntry paymentEntry) {
        return paymentService.createOrder(paymentEntry);
    }

    @PostMapping("/verifyPayment")
    public ResponseEntity<PaymentResponse> verifyPayment(@RequestBody PaymentEntry paymentEntry) {
        return paymentService.verifyPayment(paymentEntry.getOrderId(), paymentEntry.getPaymentId().toString(), paymentEntry.getSignature());
    }

}
