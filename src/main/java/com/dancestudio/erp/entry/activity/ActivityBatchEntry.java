package com.dancestudio.erp.entry.activity;

import lombok.Data;

@Data
public class ActivityBatchEntry {
    private Long batchId;
    private Long membershipPlanId;
    private Double price;
    private String name;
    private String startTime;
    private String endTime;
}
