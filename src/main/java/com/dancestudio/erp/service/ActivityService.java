package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.ActivityEntry;
import com.dancestudio.erp.response.ActivityResponse;

public interface ActivityService {

    ActivityResponse addActivity(ActivityEntry activityEntry);

    ActivityResponse updateActivity(Long activityId, ActivityEntry activityEntry);

    void deleteActivity(Long activityId);

    ActivityResponse getActivityById(Long activityId);

    ActivityResponse getAllActivities(Long studioId);
}
