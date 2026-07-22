package com.dancestudio.erp.modules.payments;

import com.dancestudio.erp.base.BaseController;
import com.dancestudio.erp.base.BaseResponse;
import com.dancestudio.erp.base.BaseService;
import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.enums.PaymentType;
import com.dancestudio.erp.modules.payments.entry.PaymentEntry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController extends BaseController<PaymentEntry, Long> {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/getAll/{branchId}")
    public ResponseEntity<BaseResponse<PaymentEntry>> getAllPayments(@PathVariable Long branchId,
            @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) PaymentType paymentType,
            @RequestParam(required = false) PaymentStatus paymentStatus,
            @RequestParam(required = false) String searchTerm) {
        return paymentService.getAll(branchId, page, size, startDate, endDate, paymentType, paymentStatus, searchTerm);
    }

    @Override
    protected BaseService<PaymentEntry, Long> getService() {
        return paymentService;
    }
}