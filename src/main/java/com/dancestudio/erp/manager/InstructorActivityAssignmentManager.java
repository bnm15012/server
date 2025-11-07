package com.dancestudio.erp.manager;

import com.dancestudio.erp.entity.InstructorActivityAssignment;
import com.dancestudio.erp.entry.InstructorActivityAssignmentEntry;

import org.springframework.data.domain.Page;

public interface InstructorActivityAssignmentManager extends BaseManager<InstructorActivityAssignmentEntry, Long> {

    InstructorActivityAssignmentEntry getInstructorAssignmentsByInstructorAndActivityId(Long instructorId, String activityName) throws Exception;

    Page<InstructorActivityAssignment> getAssignmentsByInstructor(Long instructorId, Integer page, Integer size) throws Exception;
}
