package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.StudentActivityAssignmentEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;

import java.util.List;

public interface StudentActivityAssignmentManager {

    StudentActivityAssignmentEntry addStudentActivityAssignment(StudentActivityAssignmentEntry studentActivityAssignmentEntry) throws EntityNotFoundException;

    StudentActivityAssignmentEntry updateStudentActivityAssignment(Long studentActivityAssignmentId, StudentActivityAssignmentEntry studentActivityAssignmentEntry) throws EntityNotFoundException;

    void deleteStudentActivityAssignment(Long studentActivityAssignmentId) throws EntityNotFoundException;

    StudentActivityAssignmentEntry getStudentActivityAssignmentById(Long studentActivityAssignmentId) throws EntityNotFoundException;

    StudentActivityAssignmentEntry getStudentAssignmentsByStudentAndActivityId(Long studentId, Long activityId) throws EntityNotFoundException;

    List<StudentActivityAssignmentEntry> getStudentAssignmentsByStudentId(Long studentId) throws EntityNotFoundException;

}
