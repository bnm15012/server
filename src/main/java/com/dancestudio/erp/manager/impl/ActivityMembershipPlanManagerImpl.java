package com.dancestudio.erp.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dancestudio.erp.converter.ActivityMembershipPlanConvertor;
import com.dancestudio.erp.entity.activity.ActivityMembershipPlan;
import com.dancestudio.erp.entry.activity.ActivityMembershipPlanEntry;
import com.dancestudio.erp.manager.ActivityMembershipPlanManager;
import com.dancestudio.erp.repository.Activity.ActivityMembershipRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ActivityMembershipPlanManagerImpl implements ActivityMembershipPlanManager {

    private final ActivityMembershipRepository activityMembershipRepository;

    @Autowired
    public ActivityMembershipPlanManagerImpl(ActivityMembershipRepository activityMembershipRepository) {
        this.activityMembershipRepository = activityMembershipRepository;
    }

    @Override
    public ActivityMembershipPlanEntry add(ActivityMembershipPlanEntry entry) throws Exception {
        ActivityMembershipPlan entity = ActivityMembershipPlanConvertor.convertToEntity(entry, null);
        ActivityMembershipPlan saved = activityMembershipRepository.save(entity);
        return ActivityMembershipPlanConvertor.convertToEntry(saved);
    }

    @Override
    public ActivityMembershipPlanEntry update(Long id, ActivityMembershipPlanEntry entry) throws Exception {
        ActivityMembershipPlan existing = activityMembershipRepository.findById(id)
                .orElseThrow(() -> new Exception("Membership plan not found with id: " + id));

        ActivityMembershipPlan updatedEntity = ActivityMembershipPlanConvertor.convertToEntity(entry, existing);
        ActivityMembershipPlan saved = activityMembershipRepository.save(updatedEntity);

        return ActivityMembershipPlanConvertor.convertToEntry(saved);
    }

    @Override
    public void delete(Long id) throws Exception {
        ActivityMembershipPlan existing = activityMembershipRepository.findById(id)
                .orElseThrow(() -> new Exception("Membership plan not found with id: " + id));

        activityMembershipRepository.delete(existing);
    }

    @Override
    public ActivityMembershipPlanEntry getById(Long id) throws Exception {
        ActivityMembershipPlan activityMembershipPlan = activityMembershipRepository.findById(id)
                .orElseThrow(() -> new Exception("Membership plan not found with id: " + id));
        return ActivityMembershipPlanConvertor.convertToEntry(activityMembershipPlan);
    }
}
