package com.dancestudio.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dancestudio.erp.entry.SubscriptionPlanEntry;
import com.dancestudio.erp.response.SubscriptionPlanResponse;
import com.dancestudio.erp.service.SubscriptionPlanService;

@RestController
@RequestMapping("/subscription")
public class SubscriptionPlanController {

    @Autowired
    SubscriptionPlanService subscriptionPlanService;

    @PostMapping("/createOrder")
    public ResponseEntity<SubscriptionPlanResponse> createOrder(@RequestBody SubscriptionPlanEntry subscriptionPlanEntry) {
        return subscriptionPlanService.createOrder(subscriptionPlanEntry);
    }

    @PostMapping("/verifyPayment")
    public ResponseEntity<SubscriptionPlanResponse> verifyPayment(@RequestBody SubscriptionPlanEntry subscriptionPlanEntry) {
        return subscriptionPlanService.verifyPayment(subscriptionPlanEntry.getOrderId(), subscriptionPlanEntry.getPaymentId().toString(),
        subscriptionPlanEntry.getSignature());
    }
}
