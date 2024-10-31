package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.MembershipFeeEntry;

import java.util.List;

public interface MembershipFeeManager {

    MembershipFeeEntry addMembershipFee(MembershipFeeEntry membershipFeeEntry);

    MembershipFeeEntry updateMembershipFee(Long id, Double newFeeAmount);

    List<MembershipFeeEntry> getMembershipFeesByStudio(Long studioId);

}
