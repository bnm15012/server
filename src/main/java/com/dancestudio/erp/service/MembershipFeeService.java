package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.MembershipFeeEntry;
import com.dancestudio.erp.response.MembershipFeeResponse;

public interface MembershipFeeService {

    MembershipFeeResponse addMembershipFee(MembershipFeeEntry membershipFeeEntry);

    MembershipFeeResponse updateMembershipFee(Long id, Double newFeeAmount);

    MembershipFeeResponse getMembershipFeesByStudio(Long studioId);
}
