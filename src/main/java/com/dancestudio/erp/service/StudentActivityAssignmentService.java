package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.StudentActivityAssignmentEntry;
import com.dancestudio.erp.response.StudentActivityAssignmentResponse;
import org.springframework.http.ResponseEntity;

public interface StudentActivityAssignmentService {

    ResponseEntity<StudentActivityAssignmentResponse> addStudentActivityAssignment(StudentActivityAssignmentEntry studentActivityAssignmentEntry);

    ResponseEntity<StudentActivityAssignmentResponse> updateStudentActivityAssignment(Long studentActivityAssignmentId, StudentActivityAssignmentEntry studentActivityAssignmentEntry);

    ResponseEntity<Void> deleteStudentActivityAssignment(Long studentActivityAssignmentId);

    ResponseEntity<StudentActivityAssignmentResponse> getStudentActivityAssignmentById(Long studentActivityAssignmentId);
}
