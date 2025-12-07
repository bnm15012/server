package com.dancestudio.erp.modules.member.instructor;

import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.BaseManagerInt;

import java.util.List;

public interface InstructorManager extends BaseManagerInt<InstructorEntry, Long> {

    List<InstructorEntry> getAllInstructorsByBranch(Long studioId, MembershipStatus membershipStatus, int page, int size, String searchTerm) throws EntityNotFoundException;

    Long getCountInstructorByBranchId(Long branchId);

    List<InstructorCommunicationEntry> getAllInstructorsForCommunication(Long branchId, MembershipStatus membershipStatus, int page, int size);

}
