package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.InstructorEntry;
import com.dancestudio.erp.response.InstructorResponse;

public interface InstructorService {

    InstructorResponse addInstructor(InstructorEntry instructorEntry);

    InstructorResponse updateInstructor(Long instructorId, InstructorEntry instructorEntry);

    void deleteInstructor(Long instructorId);

    InstructorResponse getInstructorById(Long instructorId);

    InstructorResponse getAllInstructors(Long studioId);
}
