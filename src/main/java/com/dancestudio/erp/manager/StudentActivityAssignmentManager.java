package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.MonthlyReportEntry;
import com.dancestudio.erp.entry.StudentActivityAssignmentEntry;

import java.util.List;

public interface StudentActivityAssignmentManager extends BaseManager<StudentActivityAssignmentEntry, Long> {

    StudentActivityAssignmentEntry getStudentAssignmentsByStudentAndActivityId(Long studentId, String activityName) throws Exception;

    List<StudentActivityAssignmentEntry> getStudentAssignmentsByStudentId(Long studentId) throws Exception;

    List<StudentActivityAssignmentEntry> getStudentByActivityIdAndStudioIdAndStatus(String activityName, Long studioId, String status) throws Exception;

    List<MonthlyReportEntry> getAnalysisReport(Integer year, Long branchId);
}
