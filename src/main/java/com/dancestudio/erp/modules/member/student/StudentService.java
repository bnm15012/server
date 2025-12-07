package com.dancestudio.erp.modules.member.student;

import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.service.BaseService;

import org.springframework.http.ResponseEntity;

public interface StudentService extends BaseService<StudentEntry, StudentResponse, Long> {

    ResponseEntity<StudentResponse> getAllStudents(Long studioId, String activityName, MembershipStatus membershipStatus, int page, int size, String searchTerm);

    ResponseEntity<StudentCommunicationResponse> getAllStudentsForCommunication(Long branchId, MembershipStatus membershipStatus, int page, int size, int birthday);

    ResponseEntity<StudentResponse> sendSubscriptionRenewalReminder(Long studentId, String activityName);
}
