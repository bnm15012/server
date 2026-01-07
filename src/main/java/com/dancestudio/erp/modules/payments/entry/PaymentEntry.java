package com.dancestudio.erp.modules.payments.entry;

import com.dancestudio.erp.enums.PayeeType;
import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.enums.PaymentType;
import com.dancestudio.erp.modules.payments.enums.TransactionType;

import lombok.Data;

import java.util.Date;

@Data
public class PaymentEntry {

    private Long id;

    private Long branchId;
    private Double amount;
    private Date paymentDate;
    private PaymentStatus status;
    private PaymentType paymentType;
    private TransactionType transactionType;

    private PayeeType payeeType; // STUDENT or BOOKING
    private Long payeeId; // bookingId OR assignmentId
    private String payeeName;

    // can be null
    private Double actualAmount;
}
