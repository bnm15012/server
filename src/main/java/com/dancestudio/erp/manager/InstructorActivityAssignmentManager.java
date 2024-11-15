package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.InstructorActivityAssignmentEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;

import java.util.List;

public interface InstructorActivityAssignmentManager {

    InstructorActivityAssignmentEntry addInstructorActivityAssignment(InstructorActivityAssignmentEntry instructorActivityAssignmentEntry) throws EntityNotFoundException;

    InstructorActivityAssignmentEntry updateInstructorActivityAssignment(Long instructorActivityAssignmentId, InstructorActivityAssignmentEntry instructorActivityAssignmentEntry) throws EntityNotFoundException;

    void deleteInstructorActivityAssignment(Long instructorActivityAssignmentId) throws EntityNotFoundException;

    InstructorActivityAssignmentEntry getInstructorActivityAssignmentById(Long instructorActivityAssignmentId) throws EntityNotFoundException;

    InstructorActivityAssignmentEntry getInstructorAssignmentsByInstructorAndActivityId(Long instructorId, Long activityId) throws EntityNotFoundException;

    List<InstructorActivityAssignmentEntry> getInstructorAssignmentsByInstructorId(Long instructorId) throws EntityNotFoundException;

}
