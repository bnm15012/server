package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.InstructorActivityAssignmentEntry;
import com.dancestudio.erp.response.InstructorActivityAssignmentResponse;
import org.springframework.http.ResponseEntity;

public interface InstructorActivityAssignmentService {

    ResponseEntity<InstructorActivityAssignmentResponse> addInstructorActivityAssignment(InstructorActivityAssignmentEntry instructorActivityAssignmentEntry);

    ResponseEntity<InstructorActivityAssignmentResponse> updateInstructorActivityAssignment(Long instructorActivityAssignmentId, InstructorActivityAssignmentEntry instructorActivityAssignmentEntry);

    ResponseEntity<Void> deleteInstructorActivityAssignment(Long instructorActivityAssignmentId);

    ResponseEntity<InstructorActivityAssignmentResponse> getInstructorActivityAssignmentById(Long instructorActivityAssignmentId);
}
