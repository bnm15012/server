package com.dancestudio.erp.service.impl;


import com.dancestudio.erp.entry.StudioSmsUsageEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.StudioSmsUsageManager;
import com.dancestudio.erp.response.StudioSmsUsageResponse;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.service.StudioSmsUsageService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Setter(onMethod = @__({@Autowired}))
@Component
public class StudioSmsUsageServiceImpl implements StudioSmsUsageService {

    private StudioSmsUsageManager studioSmsUsageManager;

    @Override
    public ResponseEntity<StudioSmsUsageResponse> add(StudioSmsUsageEntry studioSmsUsageEntry) {
        StudioSmsUsageResponse response = new StudioSmsUsageResponse();

        try {
            StudioSmsUsageEntry entry = studioSmsUsageManager.add(studioSmsUsageEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Studio Sms Usage added successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<StudioSmsUsageResponse> update(Long studioSmsUsageId, StudioSmsUsageEntry studioSmsUsageEntry) {
        StudioSmsUsageResponse response = new StudioSmsUsageResponse();

        try {
            StudioSmsUsageEntry entry = studioSmsUsageManager.update(studioSmsUsageId, studioSmsUsageEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Studio Sms Usage updated successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EntityNotFoundException e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<Void> delete(Long studioSmsUsageId) {
        try {
            studioSmsUsageManager.delete(studioSmsUsageId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<StudioSmsUsageResponse> get(Long studioSmsUsageId) {
        StudioSmsUsageResponse response = new StudioSmsUsageResponse();

        try {
            StudioSmsUsageEntry entry = studioSmsUsageManager.getById(studioSmsUsageId);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Studio Sms Usage retrieved successfully", StatusResponse.Type.SUCCESS, 1));
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
}
