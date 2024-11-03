package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.ActivityEntry;
import com.dancestudio.erp.response.ActivityResponse;
import org.springframework.http.ResponseEntity;

public interface ActivityService {

    ResponseEntity<ActivityResponse> addActivity(ActivityEntry activityEntry);

    ResponseEntity<ActivityResponse> updateActivity(Long activityId, ActivityEntry activityEntry);

    ResponseEntity<Void> deleteActivity(Long activityId);

    ResponseEntity<ActivityResponse> getActivityById(Long activityId);

    ResponseEntity<ActivityResponse> getAllActivities(Long studioId);
}
