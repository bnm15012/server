package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.PlanEntry;
import com.dancestudio.erp.response.PlanResponse;
import com.dancestudio.erp.service.BaseService;
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


    @GetMapping("/getAll")
    public ResponseEntity<PlanResponse> getAllPlans(HttpServletRequest request, @RequestParam(defaultValue = "false") Boolean AMC) {
        return planService.getAllPlans(request, AMC);
    }


    @Override
    protected BaseService<PlanEntry, PlanResponse, Long> getService() {
        return planService;
    }
}
