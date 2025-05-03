package com.dancestudio.erp.entry;

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
    private String membershipType;
}