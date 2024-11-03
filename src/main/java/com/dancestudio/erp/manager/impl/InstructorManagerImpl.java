package com.dancestudio.erp.manager.impl;


import com.dancestudio.erp.entity.Activity;
import com.dancestudio.erp.entity.BankAccount;
import com.dancestudio.erp.entity.Instructor;
import com.dancestudio.erp.entity.Studio;
import com.dancestudio.erp.entry.*;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.InstructorManager;
import com.dancestudio.erp.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class InstructorManagerImpl implements InstructorManager {
    private final InstructorRepository instructorRepository;

    @Autowired
    private StudioManagerImpl studioManagerImpl;

    @Autowired
    public InstructorManagerImpl(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    @Override
    public InstructorEntry addInstructor(InstructorEntry instructorEntry) throws EntityNotFoundException {
        Instructor instructor = convertToEntity(instructorEntry, null);
        return convertToEntry(instructorRepository.save(instructor));
    }

    @Override
    public InstructorEntry updateInstructor(Long instructorId, InstructorEntry instructorEntry) throws EntityNotFoundException {
        Instructor existingInstructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new EntityNotFoundException("Instructor not found"));

        Instructor updatedInstructor = convertToEntity(instructorEntry, existingInstructor);
        return convertToEntry(instructorRepository.save(updatedInstructor));
    }

    @Override
    public void deleteInstructor(Long instructorId) throws EntityNotFoundException {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new EntityNotFoundException("Instructor not found"));

        instructorRepository.deleteById(instructorId);
    }

    @Override
    public InstructorEntry getInstructorById(Long instructorId) throws EntityNotFoundException {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new EntityNotFoundException("Instructor not found"));

        return convertToEntry(instructor);
    }

    @Override
    public List<InstructorEntry> getAllInstructorsByStudio(Long studioId, MembershipStatus membershipStatus) {
        List<Instructor> entries = instructorRepository.findAllByStudioId(studioId, membershipStatus);

        List<InstructorEntry> instructorEntries = new ArrayList<>();
        for (Instructor entry : entries) {
            InstructorEntry instructorEntry = convertToEntry(entry);
            instructorEntries.add(instructorEntry);
        }

        return instructorEntries;
    }

    private InstructorEntry convertToEntry(Instructor instructor) {
        InstructorEntry instructorEntry = new InstructorEntry();
        instructorEntry.setInstructorId(instructor.getId());
        instructorEntry.setName(instructor.getName());
        instructorEntry.setEmail(instructor.getEmail());
        instructorEntry.setPhone(instructor.getPhone());
        instructorEntry.setImageUrl(instructor.getProfileImage());

        // Handle optional Studio
        if (instructor.getStudio() != null) {
            instructorEntry.setStudioEntry(studioManagerImpl.convertToEntry(instructor.getStudio()));
        }

        instructorEntry.setInstructorStatus(instructor.getStatus());

        // Convert Bank Account details if available
        if (instructor.getBankAccount() != null) {
            BankAccountEntry bankAccountEntry = new BankAccountEntry();
            bankAccountEntry.setAccountNumber(instructor.getBankAccount().getAccountNumber());
            bankAccountEntry.setBankName(instructor.getBankAccount().getBankName());
            bankAccountEntry.setBranchName(instructor.getBankAccount().getBranchName());
            bankAccountEntry.setIfscCode(instructor.getBankAccount().getIfscCode());
            bankAccountEntry.setUpiId(instructor.getBankAccount().getUpiId());

            instructorEntry.setBankAccountDetails(bankAccountEntry);
        }

        // Convert InstructorActivityAssignment to InstructorActivityAssignmentEntry
        if (instructor.getAssignments() != null) {
            List<InstructorActivityAssignmentEntry> assignmentEntries = instructor.getAssignments().stream()
                    .map(assignment -> {
                        InstructorActivityAssignmentEntry assignmentEntry = new InstructorActivityAssignmentEntry();
                        assignmentEntry.setAssignmentId(assignment.getId());
                        assignmentEntry.setAssignedDate(assignment.getAssignedDate());

                        // Convert Activity to ActivityEntry
                        Activity activity = assignment.getActivity();
                        ActivityEntry activityEntry = new ActivityEntry();
                        activityEntry.setActivityId(activity.getId());
                        activityEntry.setActivityType(activity.getActivityType());
                        activityEntry.setDescription(activity.getDescription());

                        assignmentEntry.setActivity(activityEntry);
                        return assignmentEntry;
                    }).collect(Collectors.toList());

            instructorEntry.setAssignments(assignmentEntries);
        }

        return instructorEntry;
    }

    private Instructor convertToEntity(InstructorEntry instructorEntry, Instructor existingInstructor) throws EntityNotFoundException {
        Instructor instructor = (existingInstructor != null) ? existingInstructor : new Instructor();

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
        if (Objects.nonNull(instructorEntry.getImageUrl())) {
            instructor.setProfileImage(instructorEntry.getImageUrl());
        }
        if (Objects.nonNull(instructorEntry.getInstructorStatus())) {
            instructor.setStatus(instructorEntry.getInstructorStatus());
        }

        // Bank account details
        if (Objects.nonNull(instructorEntry.getBankAccountDetails())) {
            BankAccount bankAccount = instructor.getBankAccount();
            if (bankAccount == null) {
                bankAccount = new BankAccount();
            }
            BankAccountEntry bankAccountEntry = instructorEntry.getBankAccountDetails();

            if (Objects.nonNull(bankAccountEntry.getAccountNumber())) {
                bankAccount.setAccountNumber(bankAccountEntry.getAccountNumber());
            }
            if (Objects.nonNull(bankAccountEntry.getBankName())) {
                bankAccount.setBankName(bankAccountEntry.getBankName());
            }
            if (Objects.nonNull(bankAccountEntry.getBranchName())) {
                bankAccount.setBranchName(bankAccountEntry.getBranchName());
            }
            if (Objects.nonNull(bankAccountEntry.getIfscCode())) {
                bankAccount.setIfscCode(bankAccountEntry.getIfscCode());
            }
            if (Objects.nonNull(bankAccountEntry.getUpiId())) {
                bankAccount.setUpiId(bankAccountEntry.getUpiId());
            }
            bankAccount.setInstructor(instructor);
            instructor.setBankAccount(bankAccount);
        }

        if (instructorEntry.getStudioEntry() != null) {
            StudioEntry studioEntry = instructorEntry.getStudioEntry();
            Studio studio = studioManagerImpl.convertToEntity(studioEntry, null);

            instructor.setStudio(studio);
        }

        return instructor;
    }

}
