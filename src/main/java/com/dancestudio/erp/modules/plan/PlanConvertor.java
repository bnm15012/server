package com.dancestudio.erp.modules.plan;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.dancestudio.erp.enums.MembershipType;
import com.dancestudio.erp.exception.EntityNotFoundException;

@Component
public class PlanConvertor {

    public static PlanEntry convertToEntry(Plan plan) throws EntityNotFoundException {

        PlanEntry planEntry = new PlanEntry();
        planEntry.setId(plan.getId());
        planEntry.setDescription(plan.getDescription());
        planEntry.setAmount(plan.getAmount());
        planEntry.setPopular(plan.getPopular());
        if (Objects.nonNull(plan.getEnabledFeatures()) && !plan.getEnabledFeatures().isEmpty()) {
            planEntry.setEnabledFeatures(new ArrayList<>(List.of(plan.getEnabledFeatures().split(","))));
        } else {
            planEntry.setEnabledFeatures(new ArrayList<>());
        }

        if (Objects.nonNull(plan.getDisabledFeatures()) && !plan.getDisabledFeatures().isEmpty()) {
            planEntry.setDisabledFeatures(new ArrayList<>(List.of(plan.getDisabledFeatures().split(","))));
        } else {
            planEntry.setDisabledFeatures(new ArrayList<>());
        }
        planEntry.setSmsQuota((long) plan.getSmsQuota());
        planEntry.setRemindBeforeDays(plan.getRemindBeforeDays());
        planEntry.setPlanType(MembershipType.valueOf(plan.getPlanType()));
        return planEntry;
    }

    public static Plan convertToEntity(PlanEntry planEntry, Plan existingPlan) throws EntityNotFoundException {
        Plan plan = (existingPlan != null) ? existingPlan : new Plan();

        if (Objects.nonNull(planEntry.getId())) {
            plan.setId(planEntry.getId());
        }
        if (Objects.nonNull(planEntry.getAmount())) {
            plan.setAmount(planEntry.getAmount());
        }
        if (Objects.nonNull(planEntry.getDescription())) {
            plan.setDescription(planEntry.getDescription());
        }
        if (Objects.nonNull(planEntry.getPopular())) {
            plan.setPopular(planEntry.getPopular());
        }
        if (Objects.nonNull(planEntry.getEnabledFeatures())) {
            plan.setEnabledFeatures(String.join(",", planEntry.getEnabledFeatures()));
        }
        if (Objects.nonNull(planEntry.getDisabledFeatures())) {
            plan.setDisabledFeatures(String.join(",", planEntry.getDisabledFeatures()));
        }
        if (Objects.nonNull(planEntry.getSmsQuota())) {
            plan.setSmsQuota(planEntry.getSmsQuota());
        }
        if (Objects.nonNull(planEntry.getPlanType())) {
            plan.setPlanType(planEntry.getPlanType().name());
        }
        if (Objects.nonNull(planEntry.getCountryCode())) {
            plan.setCountryCode(planEntry.getCountryCode());
        }
        if (Objects.nonNull(planEntry.getRemindBeforeDays())) {
            plan.setRemindBeforeDays(planEntry.getRemindBeforeDays());
        } else {
            plan.setRemindBeforeDays(0);
        }

        return plan;
    }
}
