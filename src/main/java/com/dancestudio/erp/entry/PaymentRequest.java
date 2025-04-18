package com.dancestudio.erp.entry;

import lombok.Data;

@Data
public class PaymentRequest {

    private String orderId;
    private String paymentId;
    private String signature;

}
