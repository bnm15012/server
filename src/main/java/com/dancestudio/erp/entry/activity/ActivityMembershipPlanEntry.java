package com.dancestudio.erp.entry.activity;

import java.util.List;

import com.dancestudio.erp.enums.MembershipType;
import lombok.Data;

@Data
public class ActivityMembershipPlanEntry {

    private Long membershipPlanId;
    private MembershipType membershipType;
    private Integer daysPerWeek;
    
    private Long activityId;
    private List<ActivityBatchEntry> activityBatchEntries;
}
