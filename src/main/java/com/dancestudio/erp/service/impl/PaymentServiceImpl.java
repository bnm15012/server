package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.entry.PaymentEntry;
import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;
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
    public ResponseEntity<PaymentResponse> add(PaymentEntry paymentEntry) {
        PaymentResponse response = new PaymentResponse();
        try {
            PaymentEntry entry = paymentManager.add(paymentEntry);
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
    public ResponseEntity<PaymentResponse> get(Long paymentId) {
        PaymentResponse response = new PaymentResponse();
        try {
            PaymentEntry entry = paymentManager.getById(paymentId);
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
    public ResponseEntity<PaymentResponse> update(Long paymentId, PaymentEntry paymentEntry) {
        PaymentResponse response = new PaymentResponse();

        try {
            PaymentEntry entry = paymentManager.update(paymentId, paymentEntry);
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
    public ResponseEntity<Void> delete(Long paymentId) {
        try {
            paymentManager.delete(paymentId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<PaymentResponse> getAllPayments(Long branchId, int page, int size, 
                Integer startDate, Integer startMonth, Integer startYear,
            Integer endDate, Integer endMonth, Integer endYear, String searchTerm) {
        PaymentResponse response = new PaymentResponse();
        try {
            List<PaymentEntry> entry = paymentManager.getAllPaymentsByBranch(branchId, --page, size, startDate, startMonth, startYear, endDate,
                endMonth, endYear, null, searchTerm);
            long totalCount = paymentManager.getPaymentCountByStudioId(branchId, searchTerm);
            response.setData(entry);
            response.setStatus(new StatusResponse(1, StatusResponse.Type.SUCCESS, Objects.isNull(entry) ? 0 : (int) totalCount ));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
