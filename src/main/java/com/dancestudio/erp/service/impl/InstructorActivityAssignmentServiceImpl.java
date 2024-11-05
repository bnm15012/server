package com.dancestudio.erp.service.impl;


import com.dancestudio.erp.entry.InstructorActivityAssignmentEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.InstructorActivityAssignmentManager;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.response.InstructorActivityAssignmentResponse;
import com.dancestudio.erp.service.InstructorActivityAssignmentService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Setter(onMethod = @__({@Autowired}))
@Component
public class InstructorActivityAssignmentServiceImpl implements InstructorActivityAssignmentService {

    private InstructorActivityAssignmentManager instructorActivityAssignmentManager;

    @Override
    public ResponseEntity<InstructorActivityAssignmentResponse> addInstructorActivityAssignment(InstructorActivityAssignmentEntry studentInstructorActivityAssignmentAssignmentEntry) {
        InstructorActivityAssignmentResponse response = new InstructorActivityAssignmentResponse();

        try {
            InstructorActivityAssignmentEntry entry = instructorActivityAssignmentManager.addInstructorActivityAssignment(studentInstructorActivityAssignmentAssignmentEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<InstructorActivityAssignmentResponse> updateInstructorActivityAssignment(Long studentInstructorActivityAssignmentAssignmentId, InstructorActivityAssignmentEntry studentInstructorActivityAssignmentAssignmentEntry) {
        InstructorActivityAssignmentResponse response = new InstructorActivityAssignmentResponse();

        try {
            InstructorActivityAssignmentEntry entry = instructorActivityAssignmentManager.updateInstructorActivityAssignment(studentInstructorActivityAssignmentAssignmentId, studentInstructorActivityAssignmentAssignmentEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, StatusResponse.Type.SUCCESS, 1));
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
    public ResponseEntity<Void> deleteInstructorActivityAssignment(Long studentInstructorActivityAssignmentAssignmentId) {
        try {
            instructorActivityAssignmentManager.deleteInstructorActivityAssignment(studentInstructorActivityAssignmentAssignmentId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<InstructorActivityAssignmentResponse> getInstructorActivityAssignmentById(Long studentInstructorActivityAssignmentAssignmentId) {
        InstructorActivityAssignmentResponse response = new InstructorActivityAssignmentResponse();

        try {
            InstructorActivityAssignmentEntry entry = instructorActivityAssignmentManager.getInstructorActivityAssignmentById(studentInstructorActivityAssignmentAssignmentId);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, StatusResponse.Type.SUCCESS, 1));
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
