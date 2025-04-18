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
public class PaymentController extends BaseController<PaymentEntry, PaymentResponse, Long> {

    @Autowired
    private PaymentService paymentService;

    @Override
    public ResponseEntity<PaymentResponse> add(@RequestBody PaymentEntry paymentEntry) {
        return paymentService.add(paymentEntry);
    }

    @Override
    public ResponseEntity<PaymentResponse> update(@PathVariable Long id, @RequestBody PaymentEntry paymentEntry) {
        return paymentService.update(id, paymentEntry);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return paymentService.delete(id);
    }

    @Override
    public ResponseEntity<PaymentResponse> get(@PathVariable Long id) {
        return paymentService.get(id);
    }

    @PutMapping("/updateStatus/{paymentId}")
    public ResponseEntity<PaymentResponse> updatePaymentStatus(@PathVariable Long paymentId, @PathVariable PaymentStatus status) {
        return paymentService.updatePaymentStatus(paymentId, status);
    }

    @GetMapping("/getAllPayments/{studioId}")
    public ResponseEntity<PaymentResponse> getAllPayments(@PathVariable Long studioId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "-1") int size) {
        return paymentService.getAllPayments(studioId, page, size);
    }
}