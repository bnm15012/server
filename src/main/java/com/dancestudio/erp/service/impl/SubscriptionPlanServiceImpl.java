package com.dancestudio.erp.service.impl;

import java.util.Collections;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.dancestudio.erp.entry.SubscriptionPlanEntry;
import com.dancestudio.erp.manager.SubscriptionPlanManager;
import com.dancestudio.erp.response.SubscriptionPlanResponse;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.service.SubscriptionPlanService;

import lombok.Setter;

@Setter(onMethod = @__({ @Autowired }))
@Component
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {

    private SubscriptionPlanManager subscriptionPlanManager;

    @Override
    public ResponseEntity<SubscriptionPlanResponse> createOrder(SubscriptionPlanEntry SubscriptionPlanEntry) {

        SubscriptionPlanResponse response = new SubscriptionPlanResponse();
        try {
            SubscriptionPlanEntry entry = subscriptionPlanManager.createOrder(SubscriptionPlanEntry);
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
    public ResponseEntity<SubscriptionPlanResponse> verifyPayment(String orderId, String paymentId, String signature) {
        SubscriptionPlanResponse response = new SubscriptionPlanResponse();
        try {
            SubscriptionPlanEntry entry = subscriptionPlanManager.verifyPayment(orderId, paymentId, signature);
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
