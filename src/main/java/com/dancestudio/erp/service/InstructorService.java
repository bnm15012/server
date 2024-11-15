package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.InstructorEntry;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.response.InstructorResponse;
import org.springframework.http.ResponseEntity;

public interface InstructorService {

    ResponseEntity<InstructorResponse> addInstructor(InstructorEntry instructorEntry);

    ResponseEntity<InstructorResponse> updateInstructor(Long instructorId, InstructorEntry instructorEntry);

    ResponseEntity<Void> deleteInstructor(Long instructorId);

    ResponseEntity<InstructorResponse> getInstructorById(Long instructorId);

    ResponseEntity<InstructorResponse> getAllInstructors(Long studioId, MembershipStatus membershipStatus, int page, int size);
}
