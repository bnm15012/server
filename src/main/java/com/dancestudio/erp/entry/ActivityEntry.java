package com.dancestudio.erp.entry;

import com.dancestudio.erp.enums.ActivityType;
import lombok.Data;

@Data
public class ActivityEntry {

    private Long activityId;
    private ActivityType activityType;
    private String description;
    private Long studioId;
}
