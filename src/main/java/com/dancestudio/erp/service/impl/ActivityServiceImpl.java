package com.dancestudio.erp.service.impl;


import com.dancestudio.erp.entry.ActivityEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.ActivityManager;
import com.dancestudio.erp.response.ActivityResponse;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.service.ActivityService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Setter(onMethod = @__({@Autowired}))
@Component
public class ActivityServiceImpl implements ActivityService {

    private ActivityManager activityManager;

    @Override
    public ResponseEntity<ActivityResponse> addActivity(ActivityEntry activityEntry) {
        ActivityResponse response = new ActivityResponse();

        try {
            ActivityEntry entry = activityManager.addActivity(activityEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Activity added successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<ActivityResponse> updateActivity(Long activityId, ActivityEntry activityEntry) {
        ActivityResponse response = new ActivityResponse();

        try {
            ActivityEntry entry = activityManager.updateActivity(activityId, activityEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Activity updated successfully", StatusResponse.Type.SUCCESS, 1));
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
    public ResponseEntity<Void> deleteActivity(Long activityId) {
        try {
            activityManager.deleteActivity(activityId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<ActivityResponse> getActivityById(Long activityId) {
        ActivityResponse response = new ActivityResponse();

        try {
            ActivityEntry entry = activityManager.getActivityById(activityId);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Activity retrieved successfully", StatusResponse.Type.SUCCESS, 1));
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
    public ResponseEntity<ActivityResponse> getAllActivities(Long studioId) {
        ActivityResponse response = new ActivityResponse();

        try {
            List<ActivityEntry> entries = activityManager.getAllActivities(studioId);

            response.setData(entries);
            response.setStatus(new StatusResponse(1, "Activities retrieved successfully", StatusResponse.Type.SUCCESS, entries.size()));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
