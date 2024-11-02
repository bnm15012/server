package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.entry.StudentEntry;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.StudentManager;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.response.StudentResponse;
import com.dancestudio.erp.service.StudentService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Setter(onMethod = @__({@Autowired}))
@Component
public class StudentServiceImpl implements StudentService {

    private StudentManager studentManager;

    @Override
    public ResponseEntity<StudentResponse> addStudent(StudentEntry studentEntry) {
        StudentResponse response = new StudentResponse();
        try {
            StudentEntry entry = studentManager.addStudent(studentEntry);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Student added successfully", StatusResponse.Type.SUCCESS));

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<StudentResponse> updateStudent(Long studentId, StudentEntry studentEntry) {
        StudentResponse response = new StudentResponse();

        try {
            StudentEntry entry = studentManager.updateStudent(studentId, studentEntry);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Student updated successfully", StatusResponse.Type.SUCCESS));
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
    public ResponseEntity<Void> deleteStudent(Long studentId) {
        try {
            studentManager.deleteStudent(studentId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<StudentResponse> getStudentById(Long studentId) {
        StudentResponse response = new StudentResponse();

        try {
            StudentEntry entry = studentManager.getStudentById(studentId);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Student retrieved successfully", StatusResponse.Type.SUCCESS));
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
    public ResponseEntity<StudentResponse> getAllStudents(Long studioId, Long activityId, MembershipStatus membershipStatus) {
        StudentResponse response = new StudentResponse();

        try {
            List<StudentEntry> entries = studentManager.getAllStudentsByStudio(studioId, activityId, membershipStatus);
            response.setData(entries);
            response.setStatus(new StatusResponse(1, StatusResponse.Type.SUCCESS, Objects.isNull(entries) ? 0 : entries.size()));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<StudentResponse> sendSubscriptionRenewalReminder(Long studentId) {
        StudentResponse response = new StudentResponse();
        try {
            boolean reminderSent = studentManager.sendSubscriptionRenewalReminder(studentId);

            response.setStatus(new StatusResponse(0, "Reminder not sent", StatusResponse.Type.ERROR));
            if (reminderSent) {
                response.setStatus(new StatusResponse(1, "Reminder sent successfully", StatusResponse.Type.SUCCESS));
            }
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
