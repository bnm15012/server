package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.StudentEntry;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;

import java.util.Date;
import java.util.List;

public interface StudentManager {

    StudentEntry addStudent(StudentEntry studentEntry) throws Exception;

    StudentEntry updateStudent(Long studentId, StudentEntry studentEntry) throws EntityNotFoundException;

    void deleteStudent(Long studentId) throws EntityNotFoundException;

    StudentEntry getStudentById(Long studentId) throws EntityNotFoundException;

    List<StudentEntry> getAllStudentsByStudio(Long studioId, Long activityId, MembershipStatus membershipStatus, int page, int size);

    List<StudentEntry> findByMembershipEndDate(Date reminderDate) throws EntityNotFoundException;

    boolean sendSubscriptionRenewalReminder(Long studentId, Long activityId) throws EntityNotFoundException;

    Long getAllStudentsCountByStudio(Long studioId);

}