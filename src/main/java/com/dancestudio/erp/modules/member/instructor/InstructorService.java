package com.dancestudio.erp.modules.member.instructor;

import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.service.BaseService;

import org.springframework.http.ResponseEntity;

public interface InstructorService extends BaseService<InstructorEntry, InstructorResponse, Long> {

    ResponseEntity<InstructorResponse> getAllInstructors(Long studioId, MembershipStatus membershipStatus, int page,
            int size, String searchTerm);

    ResponseEntity<InstructorCommunicationResponse> getAllInstructorsForCommunication(Long branchId,
            MembershipStatus membershipStatus, int page, int size);

}
