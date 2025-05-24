package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.StudentCommunicationEntry;
import com.dancestudio.erp.entry.StudentEntry;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;

import java.util.Date;
import java.util.List;

public interface StudentManager extends BaseManager<StudentEntry, Long> {

    List<StudentEntry> getAllStudentsByStudio(Long studioId, String activityName, MembershipStatus membershipStatus, int page, int size, String searchTerm);

    List<StudentCommunicationEntry> getAllStudentsForCommunication(Long branchId, MembershipStatus membershipStatus, int page, int size, int birthday);

    List<StudentEntry> findByMembershipEndDate(Date reminderDate) throws EntityNotFoundException;

    boolean sendSubscriptionRenewalReminder(Long studentId, String activityName) throws Exception;

    Long getAllStudentsCountByStudio(Long studioId);

}