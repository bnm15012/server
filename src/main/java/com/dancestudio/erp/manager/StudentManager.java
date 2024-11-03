package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.StudentEntry;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface StudentManager {

    StudentEntry addStudent(StudentEntry studentEntry) throws Exception;

    StudentEntry updateStudent(Long studentId, StudentEntry studentEntry) throws EntityNotFoundException;

    StudentEntry uploadImage(MultipartFile file) throws EntityNotFoundException, IOException;

    void deleteStudent(Long studentId) throws EntityNotFoundException;

    StudentEntry getStudentById(Long studentId) throws EntityNotFoundException;

    List<StudentEntry> getAllStudentsByStudio(Long studioId, Long activityId, MembershipStatus membershipStatus, int page, int size);

    List<StudentEntry> findByMembershipEndDate(LocalDate reminderDate);

    boolean sendSubscriptionRenewalReminder(Long studentId) throws EntityNotFoundException;

}