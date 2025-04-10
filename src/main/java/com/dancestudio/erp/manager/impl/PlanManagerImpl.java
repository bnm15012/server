package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.Plan;
import com.dancestudio.erp.entry.PlanEntry;
import com.dancestudio.erp.enums.MembershipType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.PlanManager;
import com.dancestudio.erp.manager.StudioManager;
import com.dancestudio.erp.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PlanManagerImpl implements PlanManager {
    private final PlanRepository planRepository;

    @Autowired
    private StudioManager studioManager;

    @Autowired
    public PlanManagerImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public PlanEntry addPlan(PlanEntry planEntry) throws EntityNotFoundException {
        Plan plan = convertToEntity(planEntry, null);
        return convertToEntry(planRepository.save(plan));
    }

    @Override
    public PlanEntry updatePlan(Long planId, PlanEntry planEntry) throws EntityNotFoundException {
        Plan existingPlan = planRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException("Plan not found"));

        Plan updatedPlan = convertToEntity(planEntry, existingPlan);
        return convertToEntry(planRepository.save(updatedPlan));
    }

    @Override
    public void deletePlan(Long planId) throws EntityNotFoundException {
        planRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException("Plan not found"));

        planRepository.deleteById(planId);
    }

    @Override
    public PlanEntry getPlanById(Long planId) throws EntityNotFoundException {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException("Plan not found"));

        return convertToEntry(plan);
    }

    @Override
    public List<PlanEntry> getAllPlans() throws EntityNotFoundException {
        List<Plan> plans = planRepository.findAll();
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
        planEntry.setDescription(plan.getDescription());
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
        if (Objects.nonNull(planEntry.getDescription())) {
            plan.setDescription(planEntry.getDescription());
        }
        if (Objects.nonNull(planEntry.getPlanType())) {
            plan.setPlanType(planEntry.getPlanType().name());
        }

        return plan;
    }
}
