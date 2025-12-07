package com.dancestudio.erp.modules.member.student;

import com.dancestudio.erp.entry.MonthlyReportEntry;
import com.dancestudio.erp.manager.BaseManagerInt;

import java.util.List;

import org.springframework.data.domain.Page;

public interface StudentActivityAssignmentManager extends BaseManagerInt<StudentActivityAssignmentEntry, Long> {

    StudentActivityAssignmentEntry getStudentAssignmentsByStudentAndActivityId(Long studentId, String activityName)
            throws Exception;

    List<StudentActivityAssignmentEntry> getStudentAssignmentsByStudentId(Long studentId) throws Exception;

    List<StudentActivityAssignmentEntry> getStudentByActivityIdAndStudioIdAndStatus(String activityName, Long studioId,
            String status) throws Exception;

    List<MonthlyReportEntry> getAnalysisReport(Integer year, Long branchId);

    Page<StudentActivityAssignment> getAssignmentsByStudentId(Long id, Integer page, Integer size)
            throws Exception;

}
