package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.converter.ActivityConverter;
import com.dancestudio.erp.entity.activity.Activity;
import com.dancestudio.erp.entry.activity.ActivityEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.ActivityManager;
import com.dancestudio.erp.repository.Activity.ActivityMembershipRepository;
import com.dancestudio.erp.repository.Activity.ActivityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityManagerImpl implements ActivityManager {

    private final ActivityMembershipRepository activityMembershipRepository;
    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityManagerImpl(ActivityRepository activityRepository,
            ActivityMembershipRepository activityMembershipRepository) {
        this.activityRepository = activityRepository;
        this.activityMembershipRepository = activityMembershipRepository;
    }

    @Override
    public ActivityEntry add(ActivityEntry activityEntry) throws Exception {
        activityRepository
                .findByActivityTypeAndBranchId(activityEntry.getActivityType().name(), activityEntry.getBranchId())
                .ifPresent(existingActivity -> {
                    throw new IllegalArgumentException("Given activity already exists in this branch.");
                });

        Activity activity = ActivityConverter.convertToEntity(activityEntry, null);
        return ActivityConverter.convertToEntry(activityRepository.save(activity));
    }

    @Override
    public ActivityEntry update(Long activityId, ActivityEntry activityEntry) throws Exception {
        Activity existingActivity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        Activity updatedActivity = ActivityConverter.convertToEntity(activityEntry, existingActivity);

        return ActivityConverter.convertToEntry(activityRepository.save(updatedActivity));
    }

    @Override
    @Transactional
    public void delete(Long activityId) throws EntityNotFoundException {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        activityRepository.delete(activity);
    }

    @Override
    public ActivityEntry getById(Long activityId) throws Exception {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        return ActivityConverter.convertToEntry(activity);
    }

    @Override
    public List<ActivityEntry> getAllActivities(Long branchId) throws Exception {
        List<Activity> entries = activityRepository.findAllByBranchId(branchId);

        List<ActivityEntry> activityEntries = new ArrayList<>();
        for (Activity entry : entries) {
            ActivityEntry activityEntry = ActivityConverter.convertToEntry(entry);
            activityEntries.add(activityEntry);
        }

        return activityEntries;
    }
}
