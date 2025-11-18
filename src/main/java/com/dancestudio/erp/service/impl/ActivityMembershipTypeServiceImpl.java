package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.converter.ActivityMembershipTypeConverter;
import com.dancestudio.erp.entity.activity.ActivityMembershipType;
import com.dancestudio.erp.entry.activity.ActivityMembershipTypeEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.ActivityMembershipTypeManager;
import com.dancestudio.erp.response.ActivityMembershipTypeResponse;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.service.ActivityMembershipTypeService;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

@Setter(onMethod = @__({ @Autowired }))
@Component
public class ActivityMembershipTypeServiceImpl implements ActivityMembershipTypeService {

    private ActivityMembershipTypeManager manager;

    @Override
    public ResponseEntity<ActivityMembershipTypeResponse> add(ActivityMembershipTypeEntry entry) {
        ActivityMembershipTypeResponse response = new ActivityMembershipTypeResponse();
        try {
            ActivityMembershipTypeEntry saved = manager.add(entry);
            response.setData(Collections.singletonList(saved));
            response.setStatus(new StatusResponse(1,
                    "ActivityMembershipType added successfully",
                    StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0,
                    e.getMessage(),
                    StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<ActivityMembershipTypeResponse> update(Long id, ActivityMembershipTypeEntry entry) {
        ActivityMembershipTypeResponse response = new ActivityMembershipTypeResponse();
        try {
            ActivityMembershipTypeEntry updated = manager.update(id, entry);
            response.setData(Collections.singletonList(updated));
            response.setStatus(new StatusResponse(1,
                    "ActivityMembershipType updated successfully",
                    StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EntityNotFoundException e) {
            response.setStatus(new StatusResponse(0,
                    e.getMessage(),
                    StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0,
                    e.getMessage(),
                    StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        try {
            manager.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<ActivityMembershipTypeResponse> get(Long id) {
        ActivityMembershipTypeResponse response = new ActivityMembershipTypeResponse();
        try {
            ActivityMembershipTypeEntry found = manager.getById(id);
            response.setData(Collections.singletonList(found));
            response.setStatus(new StatusResponse(1,
                    "ActivityMembershipType retrieved successfully",
                    StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EntityNotFoundException e) {
            response.setStatus(new StatusResponse(0,
                    e.getMessage(),
                    StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0,
                    e.getMessage(),
                    StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<ActivityMembershipTypeResponse> getAllByStudioId(Long stuidId, Integer page, Integer size) {
        ActivityMembershipTypeResponse response = new ActivityMembershipTypeResponse();
        try{
            Page<ActivityMembershipType> entries = manager.getAllByStudioId(stuidId, --page, size);
            response.setData(entries.getContent().stream().map(a-> {
                try {
                    return ActivityMembershipTypeConverter.toEntry(a);
                } catch (Exception e) {
                       throw new RuntimeException(e);
                }
            }).collect(Collectors.toList()));
            response.setStatus(new StatusResponse(1, "Instructors retrieved successfully", StatusResponse.Type.SUCCESS, Objects.isNull(entries) ? 0 : (int) entries.getTotalElements()));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0,
                    e.getMessage(),
                    StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
