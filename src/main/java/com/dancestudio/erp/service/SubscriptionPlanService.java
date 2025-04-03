package com.dancestudio.erp.service;

import org.springframework.http.ResponseEntity;

import com.dancestudio.erp.entry.SubscriptionPlanEntry;
import com.dancestudio.erp.response.SubscriptionPlanResponse;

public interface SubscriptionPlanService {
    ResponseEntity<SubscriptionPlanResponse> createOrder(SubscriptionPlanEntry subscriptionPlanEntry);

    ResponseEntity<SubscriptionPlanResponse> verifyPayment(String orderId, String paymentId, String signature);
}
