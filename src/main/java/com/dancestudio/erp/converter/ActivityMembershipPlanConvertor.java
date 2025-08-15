package com.dancestudio.erp.converter;

import com.dancestudio.erp.enums.*;
import com.dancestudio.erp.manager.impl.ActivityManagerImpl;
import com.dancestudio.erp.entity.activity.ActivityMembershipPlan;
import com.dancestudio.erp.entry.activity.ActivityEntry;
import com.dancestudio.erp.entry.activity.ActivityMembershipPlanEntry;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ActivityMembershipPlanConvertor {

    private static ApplicationContext applicationContext;

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        applicationContext = context;
    }

    public static ActivityMembershipPlanEntry convertToEntry(ActivityMembershipPlan activityMembershipPlan)
            throws Exception {
        ActivityMembershipPlanEntry activityEntry = new ActivityMembershipPlanEntry();
        activityEntry.setMembershipPlanId(activityMembershipPlan.getId());
        activityEntry.setDaysPerWeek((activityMembershipPlan.getDaysPerWeek()));
        activityEntry.setMembershipType(MembershipType.valueOf(activityMembershipPlan.getPlanType()));
        activityEntry.setActivityId(activityMembershipPlan.getId());
        return activityEntry;
    }

    public static ActivityMembershipPlan convertToEntity(ActivityMembershipPlanEntry activityMembershipPlanEntry,
            ActivityMembershipPlan existingActivity) throws Exception {
        ActivityMembershipPlan newActivityMembershipPlan = (existingActivity != null) ? existingActivity
                : new ActivityMembershipPlan();

        if (Objects.nonNull(activityMembershipPlanEntry.getMembershipPlanId())) {
            newActivityMembershipPlan.setId(activityMembershipPlanEntry.getMembershipPlanId());
        }

        if (Objects.nonNull(activityMembershipPlanEntry.getActivityId())) {
            ActivityManagerImpl activityManagerImpl = applicationContext.getBean(ActivityManagerImpl.class);
            ActivityEntry activityEntry = activityManagerImpl.getById(activityMembershipPlanEntry.getActivityId());
            newActivityMembershipPlan.setActivity(ActivityConverter.convertToEntity(activityEntry, null));
        }

        if (Objects.nonNull(activityMembershipPlanEntry.getMembershipType())) {
            newActivityMembershipPlan.setPlanType(activityMembershipPlanEntry.getMembershipType().name());
        }

        if (Objects.nonNull(activityMembershipPlanEntry.getDaysPerWeek())) {
            newActivityMembershipPlan.setDaysPerWeek(activityMembershipPlanEntry.getDaysPerWeek());
        }

        return newActivityMembershipPlan;
    }
}