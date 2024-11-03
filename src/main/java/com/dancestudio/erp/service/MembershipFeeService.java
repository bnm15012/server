package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.MembershipFeeEntry;
import com.dancestudio.erp.response.MembershipFeeResponse;
import org.springframework.http.ResponseEntity;

public interface MembershipFeeService {

    ResponseEntity<MembershipFeeResponse> addMembershipFee(MembershipFeeEntry membershipFeeEntry);

    ResponseEntity<MembershipFeeResponse> updateMembershipFee(Long id, Double newFeeAmount);

    ResponseEntity<MembershipFeeResponse> getMembershipFee(Long id);

    ResponseEntity<MembershipFeeResponse> getMembershipFeesByStudio(Long studioId);
}
