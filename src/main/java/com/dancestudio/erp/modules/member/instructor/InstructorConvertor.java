package com.dancestudio.erp.modules.member.instructor;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.dancestudio.erp.entity.Branch;
import com.dancestudio.erp.entry.BranchEntry;
import com.dancestudio.erp.enums.MemberType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.BranchManager;
import com.dancestudio.erp.modules.member.Member;
import com.dancestudio.erp.modules.member.instructor.bankAccount.BankAccountEntry;
import com.dancestudio.erp.modules.member.instructor.bankAccount.BankAccountManager;
import com.dancestudio.erp.modules.member.memberActiveStatus.MemberActiveStatusManager;
import com.dancestudio.erp.repository.BranchRepository;
import com.dancestudio.erp.util.ConvertToEntryUtil;

import jakarta.annotation.PostConstruct;

@Component
public class InstructorConvertor {

    private static ApplicationContext applicationContext;

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        applicationContext = context;
    }

    public static InstructorEntry convertToEntry(Member instructor) {
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
            BankAccountManager bankAccountManager = applicationContext.getBean(BankAccountManager.class);
            BankAccountEntry bankAccountEntry = bankAccountManager.getByInstructorId(instructor.getId());
            if (Objects.nonNull(bankAccountEntry)) {
                instructorEntry.setBankAccountDetails(bankAccountEntry);
            }

            MemberActiveStatusManager activityStatusManager = applicationContext
                    .getBean(MemberActiveStatusManager.class);
            instructorEntry.setInstructorStatus(activityStatusManager.getMembershipStatus(instructor.getId()));
        } catch (Exception ex) {
            instructorEntry.setInstructorStatus(null);
        }

        return instructorEntry;
    }

    public static Member convertToEntity(InstructorEntry instructorEntry, Member existingInstructor) throws Exception {
        Member instructor = (existingInstructor != null) ? existingInstructor : new Member();

        // add identifier as INSTRUCTOR
        instructor.setMemberType(MemberType.INSTRUCTOR.name());

        if (existingInstructor != null && Objects.nonNull(instructorEntry.getInstructorId()) && instructorEntry.getInstructorId() > 0) {
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
        if (Objects.nonNull(instructorEntry.getBranchId())) {
            BranchRepository branchRepository = applicationContext.getBean(BranchRepository.class);
            Branch branch = branchRepository.findById(instructorEntry.getBranchId())
                    .orElseThrow(() -> new EntityNotFoundException("Branch not found"));
            instructor.setBranch(branch);
        }

        return instructor;
    }

}
