package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.InstructorEntry;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface InstructorManager {

    InstructorEntry addInstructor(InstructorEntry instructorEntry) throws EntityNotFoundException;

    InstructorEntry updateInstructor(Long instructorId, InstructorEntry instructorEntry) throws EntityNotFoundException;

    InstructorEntry uploadImage(MultipartFile file) throws EntityNotFoundException, IOException;

    void deleteInstructor(Long instructorId) throws EntityNotFoundException;

    InstructorEntry getInstructorById(Long instructorId) throws EntityNotFoundException;

    List<InstructorEntry> getAllInstructorsByStudio(Long studioId, MembershipStatus membershipStatus);
}
