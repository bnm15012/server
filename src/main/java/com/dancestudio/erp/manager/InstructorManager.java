package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.InstructorEntry;

import java.util.List;

public interface InstructorManager {

    InstructorEntry addInstructor(InstructorEntry instructorEntry);

    InstructorEntry updateInstructor(Long instructorId, InstructorEntry instructorEntry);

    void deleteInstructor(Long instructorId);

    InstructorEntry getInstructorById(Long instructorId);

    List<InstructorEntry> getAllInstructorsByStudio(Long studioId);
}
