package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.PlanEntry;
import com.dancestudio.erp.response.PlanResponse;
import com.dancestudio.erp.service.PlanService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plans")
public class PlanController {

    @Autowired
    private PlanService planService;

    @PostMapping("/add")
    public ResponseEntity<PlanResponse> addPlan(@RequestBody PlanEntry planEntry) {
        return planService.addPlan(planEntry);
    }

    @PutMapping("/update/{planId}")
    public ResponseEntity<PlanResponse> updatePlan(@PathVariable Long planId, @RequestBody PlanEntry planEntry) {
        return planService.updatePlan(planId, planEntry);
    }

    @DeleteMapping("/delete/{planId}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long planId) {
        return planService.deletePlan(planId);
    }

    @GetMapping("/get/{planId}")
    public ResponseEntity<PlanResponse> getPlanById(@PathVariable Long planId) {
        return planService.getPlanById(planId);
    }

    @GetMapping("/getAllPlans")
    public ResponseEntity<PlanResponse> getAllPlans(HttpServletRequest request) {
        return planService.getAllPlans(request);
    }
}
