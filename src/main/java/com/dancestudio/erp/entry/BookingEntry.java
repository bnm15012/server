package com.dancestudio.erp.entry;

import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.enums.PaymentType;
import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
public class BookingEntry {

    private Long id;
    private Long clientId;
    private Long studioId;
    private String purpose;
    private Double totalAmount;
    private PaymentStatus paymentStatus;
    private Double advanceAmount;
    private Double balanceAmount;
    private String notes;
    private Date bookingDate;

    private Time startTime;
    private Time endTime;

    private Date advanceDate;

    private PaymentType advanceMode;

    private Date finalPaymentDate;
    private PaymentType paymentMode;

}