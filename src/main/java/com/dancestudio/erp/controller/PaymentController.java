package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.PaymentEntry;
import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.response.PaymentResponse;
import com.dancestudio.erp.service.BaseService;
import com.dancestudio.erp.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController extends BaseController<PaymentEntry, PaymentResponse, Long> {

    @Autowired
    private PaymentService paymentService;

    @PutMapping("/updateStatus/{paymentId}")
    public ResponseEntity<PaymentResponse> updatePaymentStatus(@PathVariable Long paymentId,
            @PathVariable PaymentStatus status) {
        return paymentService.updatePaymentStatus(paymentId, status);
    }

    @GetMapping("/getAll/{branchId}")
    public ResponseEntity<PaymentResponse> getAllPayments(@PathVariable Long branchId,
            @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Integer startDate,
            @RequestParam(required = false) Integer startMonth,
            @RequestParam(required = false) Integer startYear,
            @RequestParam(required = false) Integer endDate,
            @RequestParam(required = false) Integer endMonth,
            @RequestParam(required = false) Integer endYear,
            @RequestParam(required = false) String searchTerm) {
        return paymentService.getAllPayments(branchId, page, size, startDate, startMonth, startYear, endDate, endMonth,
                endYear, searchTerm);
    }

    @Override
    protected BaseService<PaymentEntry, PaymentResponse, Long> getService() {
        return paymentService;
    }
}