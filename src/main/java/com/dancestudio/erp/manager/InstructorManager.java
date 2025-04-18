package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.InstructorEntry;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;

import java.util.List;

public interface InstructorManager extends BaseManager<InstructorEntry, Long> {

    List<InstructorEntry> getAllInstructorsByStudio(Long studioId, MembershipStatus membershipStatus, int page, int size) throws EntityNotFoundException;

    Long getCountInstructorByStrudioId(Long studioId); 
}
