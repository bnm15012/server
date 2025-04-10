package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.PlanEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;

import java.util.List;

public interface PlanManager {

    PlanEntry addPlan(PlanEntry planEntry) throws EntityNotFoundException;

    PlanEntry updatePlan(Long planId, PlanEntry planEntry) throws EntityNotFoundException;

    void deletePlan(Long planId) throws EntityNotFoundException;

    PlanEntry getPlanById(Long planId) throws EntityNotFoundException;

    List<PlanEntry> getAllPlans() throws EntityNotFoundException;
}
