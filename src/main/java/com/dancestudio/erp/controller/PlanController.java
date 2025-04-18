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
public class PlanController extends BaseController<PlanEntry, PlanResponse, Long> {

    @Autowired
    private PlanService planService;

    @Override
    public ResponseEntity<PlanResponse> add(@RequestBody PlanEntry planEntry) {
        return planService.add(planEntry);
    }

    @Override
    public ResponseEntity<PlanResponse> update(@PathVariable Long id, @RequestBody PlanEntry planEntry) {
        return planService.update(id, planEntry);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return planService.delete(id);
    }

    @Override
    public ResponseEntity<PlanResponse> get(@PathVariable Long id) {
        return planService.get(id);
    }

    @GetMapping("/getAllPlans")
    public ResponseEntity<PlanResponse> getAllPlans(HttpServletRequest request) {
        return planService.getAllPlans(request);
    }
}
