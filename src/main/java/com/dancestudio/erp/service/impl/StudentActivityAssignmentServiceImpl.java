package com.dancestudio.erp.service.impl;


import com.dancestudio.erp.entry.StudentActivityAssignmentEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.StudentActivityAssignmentManager;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.response.StudentActivityAssignmentResponse;
import com.dancestudio.erp.service.StudentActivityAssignmentService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Setter(onMethod = @__({@Autowired}))
@Component
public class StudentActivityAssignmentServiceImpl implements StudentActivityAssignmentService {

    private StudentActivityAssignmentManager studentActivityAssignmentManager;

    @Override
    public ResponseEntity<StudentActivityAssignmentResponse> addStudentActivityAssignment(StudentActivityAssignmentEntry studentStudentActivityAssignmentAssignmentEntry) {
        StudentActivityAssignmentResponse response = new StudentActivityAssignmentResponse();

        try {
            StudentActivityAssignmentEntry entry = studentActivityAssignmentManager.addStudentActivityAssignment(studentStudentActivityAssignmentAssignmentEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Student Activity Added successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<StudentActivityAssignmentResponse> updateStudentActivityAssignment(Long studentStudentActivityAssignmentAssignmentId, StudentActivityAssignmentEntry studentStudentActivityAssignmentAssignmentEntry) {
        StudentActivityAssignmentResponse response = new StudentActivityAssignmentResponse();

        try {
            StudentActivityAssignmentEntry entry = studentActivityAssignmentManager.updateStudentActivityAssignment(studentStudentActivityAssignmentAssignmentId, studentStudentActivityAssignmentAssignmentEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Student Activity Updated successfully", StatusResponse.Type.SUCCESS, 1));
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
    public ResponseEntity<Void> deleteStudentActivityAssignment(Long studentStudentActivityAssignmentAssignmentId) {
        try {
            studentActivityAssignmentManager.deleteStudentActivityAssignment(studentStudentActivityAssignmentAssignmentId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<StudentActivityAssignmentResponse> getStudentActivityAssignmentById(Long studentStudentActivityAssignmentAssignmentId) {
        StudentActivityAssignmentResponse response = new StudentActivityAssignmentResponse();

        try {
            StudentActivityAssignmentEntry entry = studentActivityAssignmentManager.getStudentActivityAssignmentById(studentStudentActivityAssignmentAssignmentId);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Fetched Student Activity successfully", StatusResponse.Type.SUCCESS, 1));
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
