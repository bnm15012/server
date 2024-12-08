package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.entry.PaymentEntry;
import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.manager.PaymentManager;
import com.dancestudio.erp.response.PaymentResponse;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.service.PaymentService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Setter(onMethod = @__({ @Autowired }))
@Component
public class PaymentServiceImpl implements PaymentService {

    private PaymentManager paymentManager;

    @Override
    public ResponseEntity<PaymentResponse> addPayment(PaymentEntry paymentEntry) {
        PaymentResponse response = new PaymentResponse();
        try {
            PaymentEntry entry = paymentManager.addPayment(paymentEntry);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Payment Added successfully", StatusResponse.Type.SUCCESS,
                    Objects.isNull(entry) ? 0 : 1));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<PaymentResponse> updatePaymentStatus(Long paymentId, PaymentStatus status) {
        PaymentResponse response = new PaymentResponse();
        try {
            PaymentEntry entry = paymentManager.updatePaymentStatus(paymentId, status);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Payment updated successfully", StatusResponse.Type.SUCCESS,
                    Objects.isNull(entry) ? 0 : 1));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<PaymentResponse> getPaymentById(Long paymentId) {
        PaymentResponse response = new PaymentResponse();
        try {
            PaymentEntry entry = paymentManager.getPaymentById(paymentId);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Payment retrived successfully", StatusResponse.Type.SUCCESS,
                    Objects.isNull(entry) ? 0 : 1));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<PaymentResponse> updatePayment(Long paymentId, PaymentEntry paymentEntry) {
        PaymentResponse response = new PaymentResponse();

        try {
            PaymentEntry entry = paymentManager.updatePayment(paymentId, paymentEntry);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Payment updated successfully", StatusResponse.Type.SUCCESS,
                    Objects.isNull(entry) ? 0 : 1));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public void deletePayment(Long paymentId) {
        paymentManager.deletePayment(paymentId);
    }

    @Override
    public ResponseEntity<PaymentResponse> getAllPayments(Long studioId) {
        PaymentResponse response = new PaymentResponse();
        try {
            List<PaymentEntry> entry = paymentManager.getAllPaymentsByStudio(studioId);
            response.setData(entry);
            response.setStatus(
                    new StatusResponse(1, StatusResponse.Type.SUCCESS, Objects.isNull(entry) ? 0 : entry.size()));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<PaymentResponse> createOrder(PaymentEntry paymentEntry) {

        PaymentResponse response = new PaymentResponse();
        try {
            PaymentEntry entry = paymentManager.createOrder(paymentEntry);
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
    public ResponseEntity<PaymentResponse> verifyPayment(String orderId, String paymentId, String signature) {
        PaymentResponse response = new PaymentResponse();
        try {
            PaymentEntry entry = paymentManager.verifyPayment(orderId, paymentId, signature);
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