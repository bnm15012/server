package com.dancestudio.erp.modules.member.instructor;

import com.dancestudio.erp.manager.BaseManagerInt;

import org.springframework.data.domain.Page;

public interface InstructorActivityAssignmentManager extends BaseManagerInt<InstructorActivityAssignmentEntry, Long> {

    InstructorActivityAssignmentEntry getInstructorAssignmentsByInstructorAndActivityId(Long instructorId, String activityName) throws Exception;

    Page<InstructorActivityAssignment> getAssignmentsByInstructor(Long instructorId, Integer page, Integer size) throws Exception;
}
