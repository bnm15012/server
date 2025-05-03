package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.InstructorCommunicationEntry;
import com.dancestudio.erp.entry.InstructorEntry;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;

import java.util.List;

public interface InstructorManager extends BaseManager<InstructorEntry, Long> {

    List<InstructorEntry> getAllInstructorsByStudio(Long studioId, MembershipStatus membershipStatus, int page, int size, String searchTerm) throws EntityNotFoundException;

    Long getCountInstructorByBranchId(Long branchId);

    List<InstructorCommunicationEntry> getAllInstructorsForCommunication(Long branchId, MembershipStatus membershipStatus, int page, int size);

}
