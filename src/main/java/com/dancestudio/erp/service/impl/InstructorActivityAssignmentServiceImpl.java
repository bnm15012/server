package com.dancestudio.erp.service.impl;


import com.dancestudio.erp.converter.InstructorActivityAssignmentConvertor;
import com.dancestudio.erp.entity.InstructorActivityAssignment;
import com.dancestudio.erp.entry.InstructorActivityAssignmentEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.InstructorActivityAssignmentManager;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.response.InstructorActivityAssignmentResponse;
import com.dancestudio.erp.service.InstructorActivityAssignmentService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Setter(onMethod = @__({@Autowired}))
@Component
public class InstructorActivityAssignmentServiceImpl implements InstructorActivityAssignmentService {


    private InstructorActivityAssignmentManager instructorActivityAssignmentManager;

    @Override
    public ResponseEntity<InstructorActivityAssignmentResponse> add(InstructorActivityAssignmentEntry studentInstructorActivityAssignmentAssignmentEntry) {
        InstructorActivityAssignmentResponse response = new InstructorActivityAssignmentResponse();

        try {
            InstructorActivityAssignmentEntry entry = instructorActivityAssignmentManager.add(studentInstructorActivityAssignmentAssignmentEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Instructor Activity Added successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<InstructorActivityAssignmentResponse> update(Long studentInstructorActivityAssignmentAssignmentId, InstructorActivityAssignmentEntry studentInstructorActivityAssignmentAssignmentEntry) {
        InstructorActivityAssignmentResponse response = new InstructorActivityAssignmentResponse();

        try {
            InstructorActivityAssignmentEntry entry = instructorActivityAssignmentManager.update(studentInstructorActivityAssignmentAssignmentId, studentInstructorActivityAssignmentAssignmentEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Instructor Activity updated successfully", StatusResponse.Type.SUCCESS, 1));
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
    public ResponseEntity<Void> delete(Long studentInstructorActivityAssignmentAssignmentId) {
        try {
            instructorActivityAssignmentManager.delete(studentInstructorActivityAssignmentAssignmentId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<InstructorActivityAssignmentResponse> get(Long studentInstructorActivityAssignmentAssignmentId) {
        InstructorActivityAssignmentResponse response = new InstructorActivityAssignmentResponse();

        try {
            InstructorActivityAssignmentEntry entry = instructorActivityAssignmentManager.getById(studentInstructorActivityAssignmentAssignmentId);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Fetched Instructor Activity successfully",  StatusResponse.Type.SUCCESS, 1));
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
    public ResponseEntity<InstructorActivityAssignmentResponse> getAll(Long id, Integer page, Integer size) {

        InstructorActivityAssignmentResponse response = new InstructorActivityAssignmentResponse();
        try{
            Page<InstructorActivityAssignment> entries = instructorActivityAssignmentManager.getAssignmentsByInstructor(id, --page, size);
            response.setData(entries.getContent().stream().map(a-> {
                try {
                    return InstructorActivityAssignmentConvertor.convertToEntry(a);
                } catch (Exception e) {
                       throw new RuntimeException(e);
                }
            }).collect(Collectors.toList()));
            response.setStatus(new StatusResponse(1, "Instructors retrieved successfully", StatusResponse.Type.SUCCESS, Objects.isNull(entries) ? 0 : (int) entries.getTotalElements()));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
