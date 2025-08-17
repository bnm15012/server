package com.dancestudio.erp.entry.activity;

import com.dancestudio.erp.enums.MembershipType;

import lombok.Data;

@Data
public class ActivityBatchEntry {
    private Long batchId;
    private Long activityId;

    private MembershipType planType;
    private Integer daysPerWeek;
    private Double price;
    private String name;
    private String startTime;
    private String endTime;
}
