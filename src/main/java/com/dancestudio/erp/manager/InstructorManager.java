package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.InstructorEntry;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;

import java.util.List;

public interface InstructorManager {

    InstructorEntry addInstructor(InstructorEntry instructorEntry) throws EntityNotFoundException;

    InstructorEntry updateInstructor(Long instructorId, InstructorEntry instructorEntry) throws EntityNotFoundException;

    void deleteInstructor(Long instructorId) throws EntityNotFoundException;

    InstructorEntry getInstructorById(Long instructorId) throws EntityNotFoundException;

    List<InstructorEntry> getAllInstructorsByStudio(Long studioId, MembershipStatus membershipStatus) throws EntityNotFoundException;
}
