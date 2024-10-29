package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.ActivityEntry;

import java.util.List;

public interface ActivityManager {

    ActivityEntry addActivity(ActivityEntry activityEntry);

    ActivityEntry updateActivity(Long activityId, ActivityEntry activityEntry);

    void deleteActivity(Long activityId);

    ActivityEntry getActivityById(Long activityId);

    List<ActivityEntry> getAllActivities(Long studioId);
}
