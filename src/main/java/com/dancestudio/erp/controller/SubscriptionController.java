package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.SubscriptionEntry;
import com.dancestudio.erp.response.SubscriptionResponse;
import com.dancestudio.erp.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    SubscriptionService subscriptionService;

    @PostMapping("/createOrder")
    public ResponseEntity<SubscriptionResponse> createOrder(@RequestBody SubscriptionEntry subscriptionEntry,
            @RequestParam(value = "countryCode", defaultValue = "IN") String countryCode) {
        return subscriptionService.createOrder(subscriptionEntry, countryCode);
    }

    @PostMapping("/verifyPayment")
    public ResponseEntity<SubscriptionResponse> verifyPayment(@RequestBody SubscriptionEntry subscriptionEntry) {
        return subscriptionService.verifyPayment(subscriptionEntry.getOrderId(), subscriptionEntry.getPaymentId().toString(),
                subscriptionEntry.getSignature());
    }
}
