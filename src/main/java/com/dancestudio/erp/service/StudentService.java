package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.StudentEntry;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.response.StudentResponse;
import org.springframework.http.ResponseEntity;

public interface StudentService {

    ResponseEntity<StudentResponse> addStudent(StudentEntry studentEntry);

    ResponseEntity<StudentResponse> updateStudent(Long studentId, StudentEntry studentEntry);

    ResponseEntity<Void> deleteStudent(Long studentId);

    ResponseEntity<StudentResponse> getStudentById(Long studentId);

    ResponseEntity<StudentResponse> getAllStudents(Long studioId, Long activityId, MembershipStatus membershipStatus);

    ResponseEntity<StudentResponse> sendSubscriptionRenewalReminder(Long studentId);
}
