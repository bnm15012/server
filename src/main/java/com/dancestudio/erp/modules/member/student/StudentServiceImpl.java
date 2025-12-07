package com.dancestudio.erp.modules.member.student;

import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.response.StatusResponse;
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
    public ResponseEntity<StudentResponse> add(StudentEntry studentEntry) {
        StudentResponse response = new StudentResponse();
        try {
            StudentEntry entry = studentManager.add(studentEntry);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Student added successfully", StatusResponse.Type.SUCCESS));

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<StudentResponse> update(Long studentId, StudentEntry studentEntry) {
        StudentResponse response = new StudentResponse();

        try {
            StudentEntry entry = studentManager.update(studentId, studentEntry);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Student updated successfully", StatusResponse.Type.SUCCESS));
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
    public ResponseEntity<Void> delete(Long studentId) {
        try {
            studentManager.delete(studentId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<StudentResponse> get(Long studentId) {
        StudentResponse response = new StudentResponse();

        try {
            StudentEntry entry = studentManager.getById(studentId);
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
    public ResponseEntity<StudentResponse> getAllStudents(Long branchId, String activityName, MembershipStatus membershipStatus, int page, int size, String searchTerm) {
        StudentResponse response = new StudentResponse();

        try {
            List<StudentEntry> entries = studentManager.getAllStudentsByStudio(branchId, activityName, membershipStatus, --page, size, searchTerm);
            long totalSize = studentManager.getAllStudentsCountByStudio(branchId);
            response.setData(entries);
            response.setStatus(new StatusResponse(1, "Students retrieved successfully", StatusResponse.Type.SUCCESS, Objects.isNull(entries) ? 0 : (int) totalSize));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<StudentCommunicationResponse> getAllStudentsForCommunication(Long branchId, MembershipStatus membershipStatus, int page, int size, int birthday) {
        StudentCommunicationResponse response = new StudentCommunicationResponse();

        try {
            List<StudentCommunicationEntry> entries = studentManager.getAllStudentsForCommunication(branchId, membershipStatus, --page, size, birthday);
            long totalSize = studentManager.getAllStudentsCountByStudio(branchId);
            response.setData(entries);
            response.setStatus(new StatusResponse(1, "Students retrieved successfully", StatusResponse.Type.SUCCESS, Objects.isNull(entries) ? 0 : (int) totalSize));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<StudentResponse> sendSubscriptionRenewalReminder(Long studentId, String activityName) {
        StudentResponse response = new StudentResponse();
        try {
            boolean reminderSent = studentManager.sendSubscriptionRenewalReminder(studentId, activityName);

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
