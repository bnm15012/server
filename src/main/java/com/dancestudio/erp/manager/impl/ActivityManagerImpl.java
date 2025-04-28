package com.dancestudio.erp.manager.impl;


import com.dancestudio.erp.entity.Activity;
import com.dancestudio.erp.entry.ActivityEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.ActivityManager;
import com.dancestudio.erp.manager.BranchManager;
import com.dancestudio.erp.repository.ActivityRepository;
import com.dancestudio.erp.util.ConvertToEntryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityManagerImpl implements ActivityManager {
    private final ActivityRepository activityRepository;

    @Autowired
    private BranchManager branchManager;

    @Autowired
    public ActivityManagerImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public ActivityEntry add(ActivityEntry activityEntry) throws Exception {
        activityRepository.findByActivityTypeAndBranchId(activityEntry.getActivityType().name(), activityEntry.getBranchId())
                .ifPresent(existingActivity -> {
                    throw new IllegalArgumentException("Given activity already exists in the studio.");
                });

        Activity activity = ConvertToEntryUtil.convertToEntity(activityEntry, null);
        return ConvertToEntryUtil.convertToEntry(activityRepository.save(activity));
    }

    @Override
    public ActivityEntry update(Long activityId, ActivityEntry activityEntry) throws Exception {
        Activity existingActivity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        Activity updatedActivity = ConvertToEntryUtil.convertToEntity(activityEntry, existingActivity);
        return ConvertToEntryUtil.convertToEntry(activityRepository.save(updatedActivity));
    }

    @Override
    public void delete(Long activityId) throws EntityNotFoundException {
        activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        activityRepository.deleteById(activityId);
    }

    @Override
    public ActivityEntry getById(Long activityId) throws Exception {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        return ConvertToEntryUtil.convertToEntry(activity);
    }

    @Override
    public List<ActivityEntry> getAllActivities(Long branchId) throws Exception {
        List<Activity> entries = activityRepository.findAllByBranchId(branchId);

        List<ActivityEntry> activityEntries = new ArrayList<>();
        for (Activity entry : entries) {
            ActivityEntry activityEntry = ConvertToEntryUtil.convertToEntry(entry);
            activityEntries.add(activityEntry);
        }

        return activityEntries;
    }
}
