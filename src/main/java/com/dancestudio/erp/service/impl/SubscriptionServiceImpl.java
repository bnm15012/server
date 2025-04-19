package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.entry.SubscriptionEntry;
import com.dancestudio.erp.manager.SubscriptionManager;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.response.SubscriptionResponse;
import com.dancestudio.erp.service.SubscriptionService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;

@Setter(onMethod = @__({ @Autowired }))
@Component
public class SubscriptionServiceImpl implements SubscriptionService {

    private SubscriptionManager subscriptionManager;

    @Override
    public ResponseEntity<SubscriptionResponse> createOrder(SubscriptionEntry subscriptionEntry, String countryCode) {

        SubscriptionResponse response = new SubscriptionResponse();
        try {
            SubscriptionEntry entry = subscriptionManager.createOrder(subscriptionEntry, countryCode);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Order created successfully", StatusResponse.Type.SUCCESS,
                    Objects.isNull(entry) ? 0 : 1));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<SubscriptionResponse> verifyPayment(String orderId, String paymentId, String signature) {
        SubscriptionResponse response = new SubscriptionResponse();
        try {
            SubscriptionEntry entry = subscriptionManager.verifyPayment(orderId, paymentId, signature);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Payment verified successfully", StatusResponse.Type.SUCCESS,
                    Objects.isNull(entry) ? 0 : 1));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
