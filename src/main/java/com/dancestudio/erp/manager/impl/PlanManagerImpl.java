package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.Plan;
import com.dancestudio.erp.entry.PlanEntry;
import com.dancestudio.erp.enums.MembershipType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.PlanManager;
import com.dancestudio.erp.manager.StudioManager;
import com.dancestudio.erp.repository.PlanRepository;
import com.dancestudio.erp.util.GeoLocationUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PlanManagerImpl implements PlanManager {
    private final PlanRepository planRepository;
    private final GeoLocationUtil geoLocationUtil;

    @Autowired
    private StudioManager studioManager;

    @Autowired
    public PlanManagerImpl(PlanRepository planRepository, GeoLocationUtil geoLocationUtil) {
        this.planRepository = planRepository;
        this.geoLocationUtil = geoLocationUtil;
    }

    @Override
    public PlanEntry add(PlanEntry planEntry) throws EntityNotFoundException {
        Plan plan = convertToEntity(planEntry, null);
        return convertToEntry(planRepository.save(plan));
    }

    @Override
    public PlanEntry update(Long planId, PlanEntry planEntry) throws EntityNotFoundException {
        Plan existingPlan = planRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException("Plan not found"));

        Plan updatedPlan = convertToEntity(planEntry, existingPlan);
        return convertToEntry(planRepository.save(updatedPlan));
    }

    @Override
    public void delete(Long planId) throws EntityNotFoundException {
        planRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException("Plan not found"));

        planRepository.deleteById(planId);
    }

    @Override
    public PlanEntry getById(Long planId) throws EntityNotFoundException {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException("Plan not found"));

        return convertToEntry(plan);
    }

    @Override
    public List<PlanEntry> getAllPlans(HttpServletRequest request) throws EntityNotFoundException {

        String ip = geoLocationUtil.extractClientIp(request);
        String countryCode = geoLocationUtil.getCountryCode(ip);

        List<Plan> plans = planRepository.findByCountryCode(countryCode);
        if(CollectionUtils.isEmpty(plans)) {
            plans = planRepository.findByCountryCode("US");
        }

        List<PlanEntry> planEntries = new ArrayList<>();
        for (Plan plan : plans) {
            PlanEntry planEntry = convertToEntry(plan);
            planEntries.add(planEntry);
        }
        return planEntries;
    }

    public PlanEntry convertToEntry(Plan plan) throws EntityNotFoundException {

        PlanEntry planEntry = new PlanEntry();
        planEntry.setId(plan.getId());
        planEntry.setAmount(plan.getAmount());
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
        planEntry.setPlanType(MembershipType.valueOf(plan.getPlanType()));
        return planEntry;
    }

    private Plan convertToEntity(PlanEntry planEntry, Plan existingPlan) throws EntityNotFoundException {
        Plan plan = (existingPlan != null) ? existingPlan : new Plan();

        if (Objects.nonNull(planEntry.getId())) {
            plan.setId(planEntry.getId());
        }
        if (Objects.nonNull(planEntry.getAmount())) {
            plan.setAmount(planEntry.getAmount());
        }
        if (Objects.nonNull(planEntry.getEnabledFeatures())) {
            plan.setEnabledFeatures(String.join(",", planEntry.getEnabledFeatures()));
        }
        if (Objects.nonNull(planEntry.getDisabledFeatures())) {
            plan.setDisabledFeatures(String.join(",", planEntry.getDisabledFeatures()));
        }
        if (Objects.nonNull(planEntry.getPlanType())) {
            plan.setPlanType(planEntry.getPlanType().name());
        }
        if (Objects.nonNull(planEntry.getCountryCode())) {
            plan.setCountryCode(planEntry.getCountryCode());
        }

        return plan;
    }
}
