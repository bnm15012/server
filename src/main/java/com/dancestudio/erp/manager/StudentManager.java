package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.StudentEntry;
import com.dancestudio.erp.enums.MembershipStatus;

import java.time.LocalDate;
import java.util.List;

public interface StudentManager {

    StudentEntry addStudent(StudentEntry studentEntry);

    StudentEntry updateStudent(Long studentId, StudentEntry studentEntry);

    void deleteStudent(Long studentId);

    StudentEntry getStudentById(Long studentId);

    List<StudentEntry> getAllStudentsByStudio(Long studioId, Long activityId, MembershipStatus membershipStatus);

    List<StudentEntry> findByMembershipEndDate(LocalDate reminderDate);

    boolean sendSubscriptionRenewalReminder(Long studentId);

}