package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.StudentEntry;

import java.util.List;

public interface StudentManager {

    StudentEntry addStudent(StudentEntry studentEntry);

    StudentEntry updateStudent(Long studentId, StudentEntry studentEntry);

    void deleteStudent(Long studentId);

    StudentEntry getStudentById(Long studentId);

    List<StudentEntry> getAllStudentsByStudio(Long studioId, Long activityId);

    void resendEmail(Long studentId);

}