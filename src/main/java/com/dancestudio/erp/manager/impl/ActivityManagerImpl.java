package com.dancestudio.erp.manager.impl;


import com.dancestudio.erp.entity.Studio;
import com.dancestudio.erp.entry.ActivityEntry;
import com.dancestudio.erp.entity.Activity;
import com.dancestudio.erp.repository.ActivityRepository;
import com.dancestudio.erp.manager.ActivityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityManagerImpl implements ActivityManager {
    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityManagerImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public ActivityEntry addActivity(ActivityEntry activityEntry) {
        Activity activity = convertToEntity(activityEntry);
        return convertToEntry(activityRepository.save(activity));
    }

    @Override
    public ActivityEntry updateActivity(Long activityId, ActivityEntry activityEntry) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found"));

        Activity newActivityEntry = convertToEntity(activityEntry);
        return convertToEntry(activityRepository.save(newActivityEntry));
    }

    @Override
    public void deleteActivity(Long activityId) {
        activityRepository.deleteById(activityId);
    }

    @Override
    public ActivityEntry getActivityById(Long activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found"));
        return convertToEntry(activity);
    }

    @Override
    public List<ActivityEntry> getAllActivities() {
        List<Activity> entries = activityRepository.findAll().stream().collect(Collectors.toList());

        List<ActivityEntry> activityEntries = new ArrayList<>();
        for (Activity entry : entries) {
            ActivityEntry activityEntry = convertToEntry(entry);
            activityEntries.add(activityEntry);
        }

        return activityEntries;
    }

    private ActivityEntry convertToEntry(Activity activity) {

        ActivityEntry activityEntry = new ActivityEntry();
        activityEntry.setActivityId(activity.getId());
        activityEntry.setActivityType(activity.getActivityType());
        activityEntry.setDescription(activity.getDescription());
        activityEntry.setStudioId(activity.getStudio().getId());

        return activityEntry;
    }

    private Activity convertToEntity(ActivityEntry activityEntry) {

        Activity activity = new Activity();
        activity.setId(activityEntry.getActivityId());
        activity.setActivityType(activityEntry.getActivityType());
        activity.setDescription(activityEntry.getDescription());
        activity.setStudio(new Studio());

        return activity;
    }
}
