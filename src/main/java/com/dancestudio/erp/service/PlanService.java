package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.PlanEntry;
import com.dancestudio.erp.response.PlanResponse;
import org.springframework.http.ResponseEntity;

public interface PlanService {

    ResponseEntity<PlanResponse> addPlan(PlanEntry planEntry);

    ResponseEntity<PlanResponse> updatePlan(Long planId, PlanEntry planEntry);

    ResponseEntity<Void> deletePlan(Long planId);

    ResponseEntity<PlanResponse> getPlanById(Long planId);

    ResponseEntity<PlanResponse> getAllPlans();
}
