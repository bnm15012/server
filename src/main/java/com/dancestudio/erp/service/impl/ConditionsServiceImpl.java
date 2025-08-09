package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.entry.ConditionsEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.ConditionsManager;
import com.dancestudio.erp.response.ConditionsResponse;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.service.ConditionsService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Setter(onMethod = @__({ @Autowired }))
@Component
public class ConditionsServiceImpl implements ConditionsService {

    private ConditionsManager conditionsManager;

    @Override
    public ResponseEntity<ConditionsResponse> add(ConditionsEntry conditionsEntry) {
        ConditionsResponse response = new ConditionsResponse();

        try {
            ConditionsEntry entry = conditionsManager.add(conditionsEntry);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Conditions added successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<ConditionsResponse> update(Long id, ConditionsEntry conditionsEntry) {
        ConditionsResponse response = new ConditionsResponse();

        try {
            ConditionsEntry entry = conditionsManager.update(id, conditionsEntry);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Conditions updated successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EntityNotFoundException e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        try {
            conditionsManager.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<ConditionsResponse> get(Long id) {
        ConditionsResponse response = new ConditionsResponse();

        try {
            ConditionsEntry entry = conditionsManager.getById(id);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Conditions retrieved successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EntityNotFoundException e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @Override
    public ResponseEntity<ConditionsResponse> getAllByBranchId(Long branchId, String entityType, String activityType, int page, int size) {
        ConditionsResponse response = new ConditionsResponse();
        List<ConditionsEntry> entries;

        try {
            entries = conditionsManager.getAllConditionsByBranchId(branchId, entityType, activityType, page, size);
            long totalCount = conditionsManager.countByFilters(branchId, entityType, activityType);
            
            response.setData(entries);
            response.setStatus(new StatusResponse(1, "Conditions retrieved successfully", StatusResponse.Type.SUCCESS, (int) totalCount));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EntityNotFoundException e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
