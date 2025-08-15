package com.dancestudio.erp.entry.activity;

import java.util.List;

import com.dancestudio.erp.enums.ActivityType;
import lombok.Data;

@Data
public class ActivityEntry {

    private Long activityId;
    private ActivityType activityType;
    private String description;

    private Long branchId;
    private List<ActivityMembershipPlanEntry> membershipPlanEntry;
}
