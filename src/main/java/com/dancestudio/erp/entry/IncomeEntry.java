package com.dancestudio.erp.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeEntry {
    private String studentName;
    private String clientName;
    private Double amount;
    private String paymentMode;
    private String activityName;
    private Date paymenDate;
    private String membershipType;
}