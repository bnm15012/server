package com.dancestudio.erp.modules.activity.convertor;

import com.dancestudio.erp.entity.activity.Activity;
import com.dancestudio.erp.entity.activity.ActivityBatch;
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

    public static ActivityEntry convertToEntry(Activity activity) {
        ActivityEntry activityEntry = new ActivityEntry();
        activityEntry.setActivityId(activity.getId());
        activityEntry.setActivityType(ActivityType.valueOf(activity.getActivityType()));
        activityEntry.setDescription(activity.getDescription());
        activityEntry.setBranchId(activity.getBranch().getId());
        activityEntry.setBatchEntries(convertPlansToEntry(activity.getBatches()));
        return activityEntry;
    }

    private static List<ActivityBatchEntry> convertPlansToEntry(
            List<ActivityBatch> batches) {

        if (batches == null)
            return List.of();

        return batches.stream().map(batch -> {
            ActivityBatchEntry batchEntry = new ActivityBatchEntry();
            batchEntry.setBatchId(batch.getId());
            batchEntry.setActivityId(batch.getActivity().getId());
            batchEntry.setPrice(batch.getPrice());
            batchEntry.setName(batch.getName());
            batchEntry.setStartTime(batch.getStartTime());
            batchEntry.setPlanType((batch.getPlanType()));
            batchEntry.setDaysPerWeek(batch.getDaysPerWeek());
            batchEntry.setEndTime(batch.getEndTime());
            return batchEntry;
        }).toList();
    }

    // Converts ActivityEntry to Activity entity

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

        convertBatchesToEntity(activityEntry.getBatchEntries(), activity);

        return activity;
    }

    private static void convertBatchesToEntity(
            List<ActivityBatchEntry> batchEntries,
            Activity activity) {

        List<ActivityBatch> existingBatches = activity.getBatches();
        if (existingBatches == null) {
            existingBatches = new ArrayList<>();
            activity.setBatches(existingBatches);
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
                batch.setActivity(activity);
                existingBatches.add(batch);
            }

            batch.setPlanType(batchEntry.getPlanType());
            batch.setDaysPerWeek(batchEntry.getDaysPerWeek());

            batch.setPrice(batchEntry.getPrice());
            batch.setName(batchEntry.getName());
            batch.setStartTime(batchEntry.getStartTime());
            batch.setEndTime(batchEntry.getEndTime());
        }
    }
}
