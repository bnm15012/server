package com.dancestudio.erp.manager.impl;


import com.dancestudio.erp.entity.Activity;
import com.dancestudio.erp.entry.ActivityEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.ActivityManager;
import com.dancestudio.erp.manager.StudioManager;
import com.dancestudio.erp.repository.ActivityRepository;
import com.dancestudio.erp.util.ConvertToEntryUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityManagerImpl implements ActivityManager {
    private final ActivityRepository activityRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    private StudioManager studioManager;

    @Autowired
    public ActivityManagerImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public ActivityEntry addActivity(ActivityEntry activityEntry) throws EntityNotFoundException {
        Activity activity = ConvertToEntryUtil.convertToEntity(activityEntry, null);
        return ConvertToEntryUtil.convertToEntry(activityRepository.save(activity));
    }

    @Override
    public ActivityEntry updateActivity(Long activityId, ActivityEntry activityEntry) throws EntityNotFoundException {
        Activity existingActivity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        Activity updatedActivity = ConvertToEntryUtil.convertToEntity(activityEntry, existingActivity);
        return ConvertToEntryUtil.convertToEntry(activityRepository.save(updatedActivity));
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

        return ConvertToEntryUtil.convertToEntry(activity);
    }

    @Override
    public List<ActivityEntry> getAllActivities(Long studioId) throws EntityNotFoundException {
        List<Activity> entries = activityRepository.findAllByStudioId(studioId);

        List<ActivityEntry> activityEntries = new ArrayList<>();
        for (Activity entry : entries) {
            ActivityEntry activityEntry = ConvertToEntryUtil.convertToEntry(entry);
            activityEntries.add(activityEntry);
        }

        return activityEntries;
    }
}
