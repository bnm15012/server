package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.ActivityEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;

import java.util.List;

public interface ActivityManager {

    ActivityEntry addActivity(ActivityEntry activityEntry) throws EntityNotFoundException;

    ActivityEntry updateActivity(Long activityId, ActivityEntry activityEntry) throws EntityNotFoundException;

    void deleteActivity(Long activityId) throws EntityNotFoundException;

    ActivityEntry getActivityById(Long activityId) throws EntityNotFoundException;

    List<ActivityEntry> getAllActivities(Long studioId) throws EntityNotFoundException;
}
