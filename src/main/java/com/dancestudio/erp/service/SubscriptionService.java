package com.dancestudio.erp.service;

import org.springframework.http.ResponseEntity;

import com.dancestudio.erp.entry.SubscriptionEntry;
import com.dancestudio.erp.response.SubscriptionResponse;

public interface SubscriptionService {

    ResponseEntity<SubscriptionResponse> createOrder(SubscriptionEntry subscriptionEntry);

    ResponseEntity<SubscriptionResponse> verifyPayment(String orderId, String paymentId, String signature);
}
