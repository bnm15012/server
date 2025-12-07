package com.dancestudio.erp.modules.member.student;


import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.response.StatusResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

@Setter(onMethod = @__({@Autowired}))
@Component
public class StudentActivityAssignmentServiceImpl implements StudentActivityAssignmentService {

    private StudentActivityAssignmentManager studentActivityAssignmentManager;

    @Override
    public ResponseEntity<StudentActivityAssignmentResponse> add(StudentActivityAssignmentEntry studentStudentActivityAssignmentAssignmentEntry) {
        StudentActivityAssignmentResponse response = new StudentActivityAssignmentResponse();

        try {
            StudentActivityAssignmentEntry entry = studentActivityAssignmentManager.add(studentStudentActivityAssignmentAssignmentEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Student Activity Added successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<StudentActivityAssignmentResponse> update(Long studentStudentActivityAssignmentAssignmentId, StudentActivityAssignmentEntry studentStudentActivityAssignmentAssignmentEntry) {
        StudentActivityAssignmentResponse response = new StudentActivityAssignmentResponse();

        try {
            StudentActivityAssignmentEntry entry = studentActivityAssignmentManager.update(studentStudentActivityAssignmentAssignmentId, studentStudentActivityAssignmentAssignmentEntry);

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
    public ResponseEntity<Void> delete(Long studentStudentActivityAssignmentAssignmentId) {
        try {
            studentActivityAssignmentManager.delete(studentStudentActivityAssignmentAssignmentId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<StudentActivityAssignmentResponse> get(Long studentStudentActivityAssignmentAssignmentId) {
        StudentActivityAssignmentResponse response = new StudentActivityAssignmentResponse();

        try {
            StudentActivityAssignmentEntry entry = studentActivityAssignmentManager.getById(studentStudentActivityAssignmentAssignmentId);

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

    @Override
    public ResponseEntity<StudentActivityAssignmentResponse> getAll(Long id, Integer page, Integer size) {
     StudentActivityAssignmentResponse response = new StudentActivityAssignmentResponse();
        try{
            Page<StudentActivityAssignment> entries = studentActivityAssignmentManager.getAssignmentsByStudentId(id, --page, size);
            response.setData(entries.getContent().stream().map(a-> {
                try {
                    return StudentActivityAssignmentConvertor.convertToEntry(a);
                } catch (Exception e) {
                    e.printStackTrace();
                       throw new RuntimeException(e);
                }
            }).collect(Collectors.toList()));
            response.setStatus(new StatusResponse(1, "Instructors retrieved successfully", StatusResponse.Type.SUCCESS, Objects.isNull(entries) ? 0 : (int) entries.getTotalElements()));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    
    }
}
