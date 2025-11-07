package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.Member;
import com.dancestudio.erp.entry.*;
import com.dancestudio.erp.enums.MemberType;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.*;
import com.dancestudio.erp.repository.MemberRepository;
import com.dancestudio.erp.util.ConvertToEntryUtil;
import com.dancestudio.erp.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InstructorManagerImpl implements InstructorManager {
    private final MemberRepository memberRepository;

    @Autowired private BranchManager branchManager;
    @Autowired private StudioManager studioManager;
    @Autowired private InstructorActivityAssignmentManager instructorActivityAssignmentManager;
    @Autowired private BankAccountManager bankAccountManager;

    @Autowired
    public InstructorManagerImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public InstructorEntry add(InstructorEntry instructorEntry) throws Exception {
        if (memberRepository.findByNameAndMemberTypeAndEmail(instructorEntry.getName(), MemberType.INSTRUCTOR.name() ,instructorEntry.getEmail()).isPresent()) {
            throw new Exception("Instructor already exists");
        }

        Member instructor = convertToEntity(instructorEntry, null);
        instructor = memberRepository.save(instructor);

        if (Objects.nonNull(instructorEntry.getBankAccountDetails())) {
            instructorEntry.getBankAccountDetails().setInstructorId(instructor.getId());
            bankAccountManager.add(instructorEntry.getBankAccountDetails());
        }

        return convertToEntry(instructor);
    }

    @Override
    public InstructorEntry update(Long instructorId, InstructorEntry instructorEntry) throws Exception {
        Member existingInstructor = memberRepository.findById(instructorId)
                .orElseThrow(() -> new EntityNotFoundException("Instructor not found"));

        if (Objects.nonNull(instructorEntry.getBankAccountDetails())) {
            instructorEntry.getBankAccountDetails().setInstructorId(existingInstructor.getId());

            BankAccountEntry bankAccountEntry = null;
            try {
                bankAccountEntry = bankAccountManager.getByInstructorId(existingInstructor.getId());
            } catch (Exception ignored) {}

            if(Objects.nonNull(bankAccountEntry)) {
                bankAccountManager.update(instructorEntry.getBankAccountDetails().getBankAccountId(), instructorEntry.getBankAccountDetails());
            } else {
                bankAccountManager.add(instructorEntry.getBankAccountDetails());
            }
        }

        Member updatedInstructor = convertToEntity(instructorEntry, existingInstructor);
        updatedInstructor = memberRepository.save(updatedInstructor);
        return convertToEntry(updatedInstructor);
    }

    @Override
    public void delete(Long instructorId) throws EntityNotFoundException {
        memberRepository.findById(instructorId)
                .orElseThrow(() -> new EntityNotFoundException("Instructor not found"));

        memberRepository.deleteById(instructorId);
    }

    @Override
    public InstructorEntry getById(Long instructorId) throws EntityNotFoundException {
        Member instructor = memberRepository.findById(instructorId)
                .orElseThrow(() -> new EntityNotFoundException("Instructor not found"));

        return convertToEntry(instructor);
    }

    @Override
    public List<InstructorEntry> getAllInstructorsByBranch(Long branchId, MembershipStatus membershipStatus, int page, int size, String searchTerm) {
        if (size == -1) {
            List<Member> entries = memberRepository.findAllInstructorsByBranchIdAndOptionalActivityIdAndOptionalStatusAndSearchTerm(branchId, null, null, searchTerm);
            return entries.stream()
                    .map(this::convertToEntry)
                    .collect(Collectors.toList());
        } else {
            Pageable pageable = PageRequest.of(page, size);
            Page<Member> instructorPage = memberRepository.findAllInstructorsByBranchIdAndOptionalActivityIdAndOptionalStatusAndSearchTerm(branchId, null, membershipStatus != null ? membershipStatus.name() : null, pageable, searchTerm);
            List<InstructorEntry> entries = instructorPage.getContent().stream()
                    .map(this::convertToEntry)
                    .toList();

            if (membershipStatus == null) {
                return entries;
            }
            return entries.stream()
                    .filter(instructor -> instructor.getInstructorStatus() == membershipStatus)
                    .collect(Collectors.toList());
        }
    }

    private InstructorEntry convertToEntry(Member instructor) {
        InstructorEntry instructorEntry = new InstructorEntry();
        instructorEntry.setInstructorId(instructor.getId());
        instructorEntry.setName(instructor.getName());
        instructorEntry.setEmail(instructor.getEmail());
        instructorEntry.setDob(instructor.getDob());
        instructorEntry.setPhone(instructor.getPhone());
        instructorEntry.setImageUrl(instructor.getProfileImage());
        instructorEntry.setAddress(instructor.getAddress());
        instructorEntry.setEmergencyContactNumber(instructor.getEmergencyContactNumber());

        try {
            instructorEntry.setBranchId(instructor.getBranch().getId());

            BankAccountEntry bankAccountEntry = bankAccountManager.getByInstructorId(instructor.getId());
            if (Objects.nonNull(bankAccountEntry)) {
                instructorEntry.setBankAccountDetails(bankAccountEntry);
            }

            boolean isActive = false;

            instructorEntry.setInstructorStatus(isActive ? MembershipStatus.ACTIVE : MembershipStatus.INACTIVE);
        } catch (Exception ex) {
            instructorEntry.setInstructorStatus(null);
        }

        return instructorEntry;
    }

    private Member convertToEntity(InstructorEntry instructorEntry, Member existingInstructor) throws Exception {
        Member instructor = (existingInstructor != null) ? existingInstructor : new Member();

        // add identifier as INSTRUCTOR
        instructor.setMemberType(MemberType.INSTRUCTOR.name());

        if (Objects.nonNull(instructorEntry.getInstructorId())) {
            instructor.setId(instructorEntry.getInstructorId());
        }
        if (Objects.nonNull(instructorEntry.getName())) {
            instructor.setName(instructorEntry.getName());
        }
        if (Objects.nonNull(instructorEntry.getEmail())) {
            instructor.setEmail(instructorEntry.getEmail());
        }
        if (Objects.nonNull(instructorEntry.getPhone())) {
            instructor.setPhone(instructorEntry.getPhone());
        }
        if (Objects.nonNull(instructorEntry.getDob())) {
            instructor.setDob(instructorEntry.getDob());
        }
        if (Objects.nonNull(instructorEntry.getImageUrl())) {
            instructor.setProfileImage(instructorEntry.getImageUrl());
        }
        if (Objects.nonNull(instructorEntry.getAddress())) {
            instructor.setAddress(instructorEntry.getAddress());
        }
        if (Objects.nonNull(instructorEntry.getEmergencyContactNumber())) {
            instructor.setEmergencyContactNumber(instructorEntry.getEmergencyContactNumber());
        }

        BranchEntry branchEntry = branchManager.getById(instructorEntry.getBranchId());
        instructor.setBranch(ConvertToEntryUtil.convertToEntity(branchEntry, null));

        return instructor;
    }

    @Override
    public Long getCountInstructorByBranchId(Long branchId) {
        return memberRepository.totalInstructorsByBranchId(branchId);
    }

    @Override
    public List<InstructorCommunicationEntry> getAllInstructorsForCommunication(Long branchId, MembershipStatus membershipStatus, int page, int size) {
        List<Member> entries;
        if (size == -1) {
            entries = memberRepository.findAllInstructorsByBranchIdAndOptionalActivityIdAndOptionalStatusAndSearchTerm(branchId, null, membershipStatus.name(), null);
        } else {
            Pageable pageable = PageRequest.of(page, size);
            Page<Member> instructorPage = memberRepository.findAllInstructorsByBranchIdAndOptionalActivityIdAndOptionalStatusAndSearchTerm(branchId, null, membershipStatus.name(), pageable, null);
            entries = instructorPage.getContent().stream().toList();
        }

        List<InstructorCommunicationEntry> instructorEntries = new ArrayList<>();
        for (Member instructor : entries) {
            InstructorCommunicationEntry entry = new InstructorCommunicationEntry();
            entry.setInstructorId(instructor.getId());
            entry.setName(instructor.getName());
            instructorEntries.add(entry);
        }
        return instructorEntries;
    }
}
