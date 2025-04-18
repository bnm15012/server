package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.StudentEntry;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.response.StudentResponse;
import org.springframework.http.ResponseEntity;

public interface StudentService extends BaseService<StudentEntry, StudentResponse, Long> {

    ResponseEntity<StudentResponse> getAllStudents(Long studioId, Long activityId, MembershipStatus membershipStatus, int page, int size);

    ResponseEntity<StudentResponse> sendSubscriptionRenewalReminder(Long studentId, Long activityId);
}
