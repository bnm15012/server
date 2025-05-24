package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.InstructorActivityAssignmentEntry;

import java.util.List;

public interface InstructorActivityAssignmentManager extends BaseManager<InstructorActivityAssignmentEntry, Long> {

    InstructorActivityAssignmentEntry getInstructorAssignmentsByInstructorAndActivityId(Long instructorId, String activityName) throws Exception;

    List<InstructorActivityAssignmentEntry> getInstructorAssignmentsByInstructorId(Long instructorId) throws Exception;

}
