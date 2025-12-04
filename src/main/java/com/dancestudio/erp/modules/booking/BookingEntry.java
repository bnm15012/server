package com.dancestudio.erp.modules.booking;

import com.dancestudio.erp.entry.PaymentEntry;
import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.enums.PaymentType;
import com.dancestudio.erp.modules.client.ClientEntry;

import lombok.Data;

import java.util.Date;

@Data
public class BookingEntry {

    private Long id;
    private Long branchId;
    private String purpose;
    private Double totalAmount;
    private PaymentStatus paymentStatus;
    private Double advanceAmount;
    private Double balanceAmount;
    private String notes;
    private Date bookingDate;

    private Date startTime;
    private Date endTime;

    private Date advanceDate;

    private PaymentType advanceMode;

    private Date finalPaymentDate;
    private PaymentType paymentMode;

    private PaymentEntry paymentEntry;
    private ClientEntry clientEntry;
}