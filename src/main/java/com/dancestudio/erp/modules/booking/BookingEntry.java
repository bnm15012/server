package com.dancestudio.erp.modules.booking;

import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.modules.client.ClientEntry;
import com.dancestudio.erp.modules.payments.entry.PaymentEntry;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BookingEntry {

    private Long id;
    private Long branchId;
    private String purpose;
    private Double totalAmount;
    private PaymentStatus paymentStatus;
    private String notes;
    private Date bookingDate;

    private Date startTime;
    private Date endTime;

    private ClientEntry clientEntry;
    private List<PaymentEntry> paymentEntries;
    private String invoiceToken;
}