package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.entry.SubscriptionEntry;
import com.dancestudio.erp.enums.SubscriptionStatus;
import com.dancestudio.erp.enums.SubscriptionType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.StudioManager;
import com.dancestudio.erp.manager.SubscriptionManager;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.response.StudioResponse;
import com.dancestudio.erp.service.StudioService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Setter(onMethod = @__({@Autowired}))
@Component
public class StudioServiceImpl implements StudioService {

    private StudioManager studioManager;
    private SubscriptionManager subscriptionManager;

    @Override
    public ResponseEntity<StudioResponse> add(StudioEntry studioEntry) {
        StudioResponse response = new StudioResponse();

        try {
            StudioEntry entry = studioManager.add(studioEntry);
            SubscriptionEntry subscriptionEntry = createTrialSubscriptionPlan(entry.getStudioId());
            entry.setSubscriptionEntry(subscriptionEntry);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Studio added successfully", StatusResponse.Type.SUCCESS));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<StudioResponse> update(Long studioId, StudioEntry studioEntry) {
        StudioResponse response = new StudioResponse();

        try {
            StudioEntry entry = studioManager.update(studioId, studioEntry);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Studio updated successfully", StatusResponse.Type.SUCCESS));
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<Void> delete(Long studioId) {
        try {
            studioManager.delete(studioId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<StudioResponse> get(Long studioId) {
        StudioResponse response = new StudioResponse();

        try {
            StudioEntry entry = studioManager.getById(studioId);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Studio retrieved successfully", StatusResponse.Type.SUCCESS));
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<StudioResponse> getAllStudios() {
        StudioResponse response = new StudioResponse();

        try {
            List<StudioEntry> entry = studioManager.getAllStudios();
            response.setData(entry);
            response.setStatus(new StatusResponse(1, StatusResponse.Type.SUCCESS, entry.size()));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    private SubscriptionEntry createTrialSubscriptionPlan(Long studioId) throws Exception {
        SubscriptionEntry trialPlan = new SubscriptionEntry();
        trialPlan.setSubscriptionPlan(SubscriptionType.TRIAL);
        trialPlan.setStudioId(studioId);
        trialPlan.setStatus(SubscriptionStatus.ACTIVE);
        trialPlan.setOrderId("TRIAL-" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6));
        trialPlan.setPrice(0.0);

        return subscriptionManager.add(trialPlan);
    }

}
