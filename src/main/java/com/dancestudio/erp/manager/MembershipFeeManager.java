package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.MembershipFeeEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;

import java.util.List;

public interface MembershipFeeManager {

    MembershipFeeEntry addMembershipFee(MembershipFeeEntry membershipFeeEntry) throws EntityNotFoundException;

    MembershipFeeEntry updateMembershipFee(Long id, Double newFeeAmount) throws EntityNotFoundException;

    MembershipFeeEntry getMembershipFeeById(Long id) throws EntityNotFoundException;

    List<MembershipFeeEntry> getMembershipFeesByStudio(Long studioId);

}
