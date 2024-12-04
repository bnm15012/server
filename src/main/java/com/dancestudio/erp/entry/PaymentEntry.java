package com.dancestudio.erp.entry;

import com.dancestudio.erp.enums.PayeeType;
import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.enums.PaymentType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentEntry {

    private Long paymentId;
    private PayeeType payeeType;
    private Long payeeId;
    private Double amount;
    private LocalDate paymentDate;
    private PaymentStatus status;
    private PaymentType paymentType;
    private String message;
}
