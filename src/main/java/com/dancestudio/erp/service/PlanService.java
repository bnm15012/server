package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.PlanEntry;
import com.dancestudio.erp.response.PlanResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface PlanService extends BaseService<PlanEntry, PlanResponse, Long> {

    ResponseEntity<PlanResponse> getAllPlans(HttpServletRequest request);
}
