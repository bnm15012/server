package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.InstructorEntry;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.response.InstructorResponse;
import org.springframework.http.ResponseEntity;

public interface InstructorService extends BaseService<InstructorEntry, InstructorResponse, Long> {

    ResponseEntity<InstructorResponse> getAllInstructors(Long studioId, MembershipStatus membershipStatus, int page, int size);
}
