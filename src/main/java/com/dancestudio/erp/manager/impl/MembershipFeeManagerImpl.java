package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.MembershipFee;
import com.dancestudio.erp.entry.MembershipFeeEntry;
import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.manager.MembershipFeeManager;
import com.dancestudio.erp.repository.MembershipFeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MembershipFeeManagerImpl implements MembershipFeeManager {

    @Autowired
    private final MembershipFeeRepository membershipFeeRepository;

    @Autowired
    private StudioManagerImpl studioManager;

    @Autowired
    public MembershipFeeManagerImpl(MembershipFeeRepository membershipFeeRepository) {
        this.membershipFeeRepository = membershipFeeRepository;
    }

    @Override
    public MembershipFeeEntry addMembershipFee(MembershipFeeEntry membershipFeeEntry) {
        MembershipFee membershipFee = convertToEntity(membershipFeeEntry);
        membershipFeeRepository.save(membershipFee);
        return convertToEntry(membershipFee);
    }

    @Override
    public MembershipFeeEntry updateMembershipFee(Long id, Double newFeeAmount) {
        MembershipFee membershipFee = membershipFeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membership Fee not found"));

        membershipFee.setFeeAmount(newFeeAmount);
        return convertToEntry(membershipFeeRepository.save(membershipFee));
    }

    @Override
    public List<MembershipFeeEntry> getMembershipFeesByStudio(Long studioId) {
        List<MembershipFee> membershipFees = membershipFeeRepository.findByStudioId(studioId);
        return membershipFees.stream()
                .map(this::convertToEntry)
                .collect(Collectors.toList());
    }

    private MembershipFeeEntry convertToEntry(MembershipFee membershipFee) {

        MembershipFeeEntry membershipFeeEntry = new MembershipFeeEntry();

        membershipFeeEntry.setMembershipType(membershipFee.getMembershipType());
        membershipFeeEntry.setAmount(membershipFee.getFeeAmount());
        membershipFeeEntry.setStudioId(membershipFee.getStudio().getId());

        return membershipFeeEntry;
    }

    private MembershipFee convertToEntity(MembershipFeeEntry membershipFeeEntry) {
        MembershipFee membershipFee = new MembershipFee();

        StudioEntry studioEntry = studioManager.getStudioById(membershipFeeEntry.getStudioId());
        membershipFee.setStudio(studioManager.convertToEntity(studioEntry));

        membershipFee.setMembershipType(membershipFeeEntry.getMembershipType());
        membershipFee.setFeeAmount(membershipFeeEntry.getAmount());
        return membershipFee;
    }

}