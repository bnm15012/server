package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.entry.PlanEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.PlanManager;
import com.dancestudio.erp.response.PlanResponse;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.service.PlanService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Setter(onMethod = @__({ @Autowired }))
@Component
public class PlanServiceImpl implements PlanService {

    private PlanManager planManager;

    @Override
    public ResponseEntity<PlanResponse> add(PlanEntry planEntry) {
        PlanResponse response = new PlanResponse();

        try {
            PlanEntry entry = planManager.add(planEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Plan added successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<PlanResponse> update(Long planId, PlanEntry planEntry) {
        PlanResponse response = new PlanResponse();

        try {
            PlanEntry entry = planManager.update(planId, planEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Plan updated successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EntityNotFoundException e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<Void> delete(Long planId) {
        try {
            planManager.delete(planId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<PlanResponse> get(Long planId) {
        PlanResponse response = new PlanResponse();

        try {
            PlanEntry entry = planManager.getById(planId);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Plan retrieved successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EntityNotFoundException e) {
            response.setData(Collections.emptyList());
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<PlanResponse> getAllPlans(HttpServletRequest request) {
        PlanResponse response = new PlanResponse();

        try {
            List<PlanEntry> entries = planManager.getAllPlans(request);
            response.setData(entries);
            response.setStatus(new StatusResponse(1, "Plans retrieved successfully", StatusResponse.Type.SUCCESS, (int) entries.size()));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
