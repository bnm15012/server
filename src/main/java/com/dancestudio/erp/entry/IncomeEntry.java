package com.dancestudio.erp.entry;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeEntry {
    private String studentName;
    private Double amount;
    private String paymentMode;
    private String activityName;
    private Date paymenDate;
    private String membershipType;
}