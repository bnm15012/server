package com.dancestudio.erp.converter;

import com.dancestudio.erp.entity.activity.Activity;
import com.dancestudio.erp.entity.activity.ActivityBatch;
import com.dancestudio.erp.entity.activity.ActivityMembershipPlan;
import com.dancestudio.erp.entry.activity.*;
import com.dancestudio.erp.enums.*;
import com.dancestudio.erp.util.ConvertToEntryUtil;
import com.dancestudio.erp.manager.impl.BranchManagerImpl;
import com.dancestudio.erp.entry.BranchEntry;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class ActivityConverter {

    private static ApplicationContext applicationContext;

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        applicationContext = context;
    }

    public static ActivityEntry convertToEntry(Activity activity) throws Exception {
        ActivityEntry activityEntry = new ActivityEntry();
        activityEntry.setActivityId(activity.getId());
        activityEntry.setActivityType(ActivityType.valueOf(activity.getActivityType()));
        activityEntry.setDescription(activity.getDescription());
        activityEntry.setBranchId(activity.getBranch().getId());

        activityEntry.setMembershipPlanEntry(convertPlansToEntry(activity.getMembershipPlan()));

        return activityEntry;
    }

    public static Activity convertToEntity(ActivityEntry activityEntry, Activity existingActivity) throws Exception {
        Activity activity = (existingActivity != null) ? existingActivity : new Activity();

        if (Objects.nonNull(activityEntry.getActivityId())) {
            activity.setId(activityEntry.getActivityId());
        }
        if (Objects.nonNull(activityEntry.getActivityType())) {
            activity.setActivityType(activityEntry.getActivityType().name());
        }
        if (Objects.nonNull(activityEntry.getDescription())) {
            activity.setDescription(activityEntry.getDescription());
        }

        if (Objects.nonNull(activityEntry.getBranchId())) {
            BranchManagerImpl branchManagerImpl = applicationContext.getBean(BranchManagerImpl.class);
            BranchEntry branchEntry = branchManagerImpl.getById(activityEntry.getBranchId());
            activity.setBranch(ConvertToEntryUtil.convertToEntity(branchEntry, null));
        }

        convertPlansToEntity(activityEntry.getMembershipPlanEntry(), activity);

        return activity;
    }

    // ---------- PRIVATE HELPERS ----------
    private static void convertPlansToEntity(
            List<ActivityMembershipPlanEntry> planEntries,
            Activity activity) {

        List<ActivityMembershipPlan> existingPlans = activity.getMembershipPlan() != null
                ? activity.getMembershipPlan()
                : new ArrayList<>();

        // Remove plans not present in DTO
        existingPlans.removeIf(existing -> planEntries.stream()
                .noneMatch(dto -> dto.getMembershipPlanId() != null &&
                        dto.getMembershipPlanId().equals(existing.getId())));

        // Update existing or add new
        for (ActivityMembershipPlanEntry planEntry : planEntries) {
            ActivityMembershipPlan plan = null;

            if (planEntry.getMembershipPlanId() != null) {
                plan = existingPlans.stream()
                        .filter(p -> planEntry.getMembershipPlanId().equals(p.getId()))
                        .findFirst()
                        .orElse(null);
            }

            if (plan == null) {
                plan = new ActivityMembershipPlan();
                plan.setActivity(activity);
                existingPlans.add(plan);
            }

            plan.setPlanType(planEntry.getMembershipType().name());
            plan.setDaysPerWeek(planEntry.getDaysPerWeek());

            convertBatchesToEntity(planEntry.getActivityBatchEntries(), plan);
        }

        if (activity.getMembershipPlan() == null) {
            activity.setMembershipPlan(existingPlans);
        }
    }

    private static void convertBatchesToEntity(
            List<ActivityBatchEntry> batchEntries,
            ActivityMembershipPlan plan) {

        List<ActivityBatch> existingBatches = plan.getBatches();
        if (existingBatches == null) {
            existingBatches = new ArrayList<>();
            plan.setBatches(existingBatches);
        }

        // Remove batches not present in incoming list
        existingBatches.removeIf(
                existing -> batchEntries.stream().noneMatch(dto -> Objects.equals(dto.getBatchId(), existing.getId())));

        // Update existing or add new
        for (ActivityBatchEntry batchEntry : batchEntries) {
            ActivityBatch batch = null;

            if (batchEntry.getBatchId() != null) {
                batch = existingBatches.stream()
                        .filter(b -> Objects.equals(b.getId(), batchEntry.getBatchId()))
                        .findFirst()
                        .orElse(null);
            }

            if (batch == null) {
                batch = new ActivityBatch();
                batch.setMembershipPlan(plan);
                existingBatches.add(batch);
            }

            batch.setPrice(batchEntry.getPrice());
            batch.setName(batchEntry.getName());
            batch.setStartTime(batchEntry.getStartTime());
            batch.setEndTime(batchEntry.getEndTime());
        }
    }

    private static List<ActivityMembershipPlanEntry> convertPlansToEntry(
            List<ActivityMembershipPlan> plans) {
        if (plans == null)
            return List.of();

        return plans.stream().map(plan -> {
            ActivityMembershipPlanEntry planEntry = new ActivityMembershipPlanEntry();
            planEntry.setMembershipPlanId(plan.getId());
            planEntry.setMembershipType(MembershipType.valueOf(plan.getPlanType()));
            planEntry.setDaysPerWeek(plan.getDaysPerWeek());
            planEntry.setActivityId(plan.getActivity().getId());
            planEntry.setActivityBatchEntries(
                    convertBatchesToEntry(plan.getBatches()));
            return planEntry;
        }).toList();
    }

    private static List<ActivityBatchEntry> convertBatchesToEntry(
            List<ActivityBatch> batches) {
        if (batches == null)
            return List.of();

        return batches.stream().map(batch -> {
            ActivityBatchEntry batchEntry = new ActivityBatchEntry();
            batchEntry.setBatchId(batch.getId());
            batchEntry.setMembershipPlanId(batch.getMembershipPlan().getId());
            batchEntry.setPrice(batch.getPrice());
            batchEntry.setName(batch.getName());
            batchEntry.setStartTime(batch.getStartTime());
            batchEntry.setEndTime(batch.getEndTime());
            return batchEntry;
        }).toList();
    }
}
