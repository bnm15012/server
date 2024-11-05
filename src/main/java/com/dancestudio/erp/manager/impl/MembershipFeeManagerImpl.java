package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.MembershipFee;
import com.dancestudio.erp.entry.MembershipFeeEntry;
import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.enums.MembershipType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.MembershipFeeManager;
import com.dancestudio.erp.manager.StudioManager;
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
    private StudioManager studioManager;

    @Autowired
    public MembershipFeeManagerImpl(MembershipFeeRepository membershipFeeRepository) {
        this.membershipFeeRepository = membershipFeeRepository;
    }

    @Override
    public MembershipFeeEntry addMembershipFee(MembershipFeeEntry membershipFeeEntry) throws EntityNotFoundException {
        MembershipFee membershipFee = convertToEntity(membershipFeeEntry, null);
        membershipFee = membershipFeeRepository.save(membershipFee);
        return convertToEntry(membershipFee);
    }

    @Override
    public MembershipFeeEntry updateMembershipFee(Long id, Double newFeeAmount) throws EntityNotFoundException {
        MembershipFee existingMembershipFee = membershipFeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Membership Fee not found"));

        existingMembershipFee.setFeeAmount(newFeeAmount);
        return convertToEntry(membershipFeeRepository.save(existingMembershipFee));
    }

    @Override
    public MembershipFeeEntry getMembershipFeeById(Long id) throws EntityNotFoundException {
        MembershipFee membershipFee = membershipFeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Membership Fee not found"));

        return convertToEntry(membershipFee);
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

        membershipFeeEntry.setMemberShipId(membershipFee.getId());
        membershipFeeEntry.setMembershipType(MembershipType.valueOf(membershipFee.getMembershipType()));
        membershipFeeEntry.setAmount(membershipFee.getFeeAmount());
        membershipFeeEntry.setStudioId(membershipFee.getStudioId());

        return membershipFeeEntry;
    }

    private MembershipFee convertToEntity(MembershipFeeEntry membershipFeeEntry, MembershipFee existingMembershipFee) throws EntityNotFoundException {
        MembershipFee membershipFee = (existingMembershipFee != null) ? existingMembershipFee : new MembershipFee();

        if (membershipFeeEntry.getStudioId() != null) {
            StudioEntry studioEntry = studioManager.getStudioById(membershipFeeEntry.getStudioId());
            membershipFee.setStudioId(studioEntry.getStudioId());
        }
        if (membershipFeeEntry.getMembershipType() != null) {
            membershipFee.setMembershipType(membershipFeeEntry.getMembershipType().name());
        }
        if (membershipFeeEntry.getAmount() != null) {
            membershipFee.setFeeAmount(membershipFeeEntry.getAmount());
        }

        return membershipFee;
    }

}