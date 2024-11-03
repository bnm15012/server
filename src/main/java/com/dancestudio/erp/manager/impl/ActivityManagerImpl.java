package com.dancestudio.erp.manager.impl;


import com.dancestudio.erp.entity.Activity;
import com.dancestudio.erp.entry.ActivityEntry;
import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.ActivityManager;
import com.dancestudio.erp.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ActivityManagerImpl implements ActivityManager {
    private final ActivityRepository activityRepository;

    @Autowired
    private StudioManagerImpl studioManagerImpl;

    @Autowired
    public ActivityManagerImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public ActivityEntry addActivity(ActivityEntry activityEntry) throws EntityNotFoundException {
        Activity activity = convertToEntity(activityEntry, null);
        return convertToEntry(activityRepository.save(activity));
    }

    @Override
    public ActivityEntry updateActivity(Long activityId, ActivityEntry activityEntry) throws EntityNotFoundException {
        Activity existingActivity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        Activity updatedActivity = convertToEntity(activityEntry, existingActivity);
        return convertToEntry(activityRepository.save(updatedActivity));
    }

    @Override
    public void deleteActivity(Long activityId) throws EntityNotFoundException {
        activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        activityRepository.deleteById(activityId);
    }

    @Override
    public ActivityEntry getActivityById(Long activityId) throws EntityNotFoundException {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        return convertToEntry(activity);
    }

    @Override
    public List<ActivityEntry> getAllActivities(Long studioId) {
        List<Activity> entries = activityRepository.findAllByStudioId(studioId);

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

    private Activity convertToEntity(ActivityEntry activityEntry, Activity existingActivity) throws EntityNotFoundException {
        Activity activity = (existingActivity != null) ? existingActivity : new Activity();

        if (Objects.nonNull(activityEntry.getActivityId())) {
            activity.setId(activityEntry.getActivityId());
        }
        if (Objects.nonNull(activityEntry.getActivityType())) {
            activity.setActivityType(activityEntry.getActivityType());
        }
        if (Objects.nonNull(activityEntry.getDescription())) {
            activity.setDescription(activityEntry.getDescription());
        }

        if (activityEntry.getStudioId() != null) {
            StudioEntry studioEntry = studioManagerImpl.getStudioById(activityEntry.getStudioId());
            activity.setStudio(studioManagerImpl.convertToEntity(studioEntry, null));
        }

        return activity;
    }
}
