package com.dancestudio.erp.converter;

import com.dancestudio.erp.manager.impl.ActivityManagerImpl;
import jakarta.annotation.PostConstruct;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.dancestudio.erp.entity.activity.ActivityBatch;
import com.dancestudio.erp.enums.MembershipType;
import com.dancestudio.erp.entry.activity.ActivityBatchEntry;
import com.dancestudio.erp.entry.activity.ActivityEntry;

@Component
public class ActivityBatchConvertor {

    private static ApplicationContext applicationContext;

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        applicationContext = context;
    }

    public static ActivityBatchEntry convertToEntry(ActivityBatch activityBatch)
            throws Exception {
        ActivityBatchEntry activityBatchEntry = new ActivityBatchEntry();

        activityBatchEntry.setBatchId(activityBatch.getId());
        activityBatchEntry.setActivityId(activityBatch.getActivity().getId());
      
        activityBatchEntry.setDaysPerWeek(activityBatch.getDaysPerWeek());
        activityBatchEntry.setPlanType(MembershipType.valueOf(activityBatch.getPlanType()));
        activityBatchEntry.setPrice((activityBatch.getPrice()));
        activityBatchEntry.setName((activityBatch.getName()));
        activityBatchEntry.setStartTime(activityBatch.getStartTime());
        activityBatchEntry.setEndTime(activityBatch.getEndTime());
        return activityBatchEntry;
    }

    public static ActivityBatch convertToEntity(ActivityBatchEntry activityBatchEntry,
            ActivityBatch existingBatch) throws Exception {
        ActivityBatch newActivityBatch = (existingBatch != null) ? existingBatch
                : new ActivityBatch();

        if (Objects.nonNull(activityBatchEntry.getBatchId())) {
            newActivityBatch.setId(activityBatchEntry.getBatchId());
        }

        if (Objects.nonNull(activityBatchEntry.getPlanType())) {
            newActivityBatch.setPlanType(activityBatchEntry.getPlanType().name());
        }
        if (Objects.nonNull(activityBatchEntry.getDaysPerWeek())) {
            newActivityBatch.setDaysPerWeek(activityBatchEntry.getDaysPerWeek());
        }
        if (Objects.nonNull(activityBatchEntry.getPrice())) {
            newActivityBatch.setPrice(activityBatchEntry.getPrice());
        }
        if (Objects.nonNull(activityBatchEntry.getName())) {
            newActivityBatch.setName(activityBatchEntry.getName());
        }
        if (Objects.nonNull(activityBatchEntry.getStartTime())) {
            newActivityBatch.setStartTime(activityBatchEntry.getStartTime());
        }
        if (Objects.nonNull(activityBatchEntry.getEndTime())) {
            newActivityBatch.setEndTime(activityBatchEntry.getEndTime());
        }

        if (Objects.nonNull(activityBatchEntry.getActivityId())) {
            ActivityManagerImpl activityManagerImpl = applicationContext.getBean(ActivityManagerImpl.class);
            ActivityEntry activityEntry = activityManagerImpl.getById(activityBatchEntry.getActivityId());
            newActivityBatch.setActivity(ActivityConverter.convertToEntity(activityEntry, null));
        }
        return newActivityBatch;
    }

}
