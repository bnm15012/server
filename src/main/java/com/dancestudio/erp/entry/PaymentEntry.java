package com.dancestudio.erp.entry;

import com.dancestudio.erp.enums.PayeeType;
import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.enums.PaymentType;
import lombok.Data;

import java.util.Date;

@Data
public class PaymentEntry {

    private String paymentId;
    private PayeeType payeeType;
    private Long payeeId;
    private Double amount;
    private Date paymentDate;
    private PaymentStatus status;
    private PaymentType paymentType;
    private String message;
    private Long studioId;
}
