package com.dancestudio.erp.manager.impl;


import com.dancestudio.erp.entity.Activity;
import com.dancestudio.erp.entity.BankAccount;
import com.dancestudio.erp.entity.Instructor;
import com.dancestudio.erp.entry.ActivityEntry;
import com.dancestudio.erp.entry.InstructorActivityAssignmentEntry;
import com.dancestudio.erp.entry.InstructorEntry;
import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.manager.InstructorManager;
import com.dancestudio.erp.manager.StudioManager;
import com.dancestudio.erp.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstructorManagerImpl implements InstructorManager {
    private final InstructorRepository instructorRepository;

    @Autowired
    private StudioManager studioManager;

    @Autowired
    private StudioManagerImpl studioManagers;


    @Autowired
    public InstructorManagerImpl(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    @Override
    public InstructorEntry addInstructor(InstructorEntry instructorEntry) {
        Instructor instructor = convertToEntity(instructorEntry);
        return convertToEntry(instructorRepository.save(instructor));
    }

    @Override
    public InstructorEntry updateInstructor(Long instructorId, InstructorEntry instructorEntry) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        Instructor newInstructorEntry = convertToEntity(instructorEntry);
        return convertToEntry(instructorRepository.save(newInstructorEntry));
    }

    @Override
    public void deleteInstructor(Long instructorId) {
        instructorRepository.deleteById(instructorId);
    }

    @Override
    public InstructorEntry getInstructorById(Long instructorId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

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
        instructorEntry.setName(instructor.getName());
        instructorEntry.setEmail(instructor.getEmail());
        instructorEntry.setPhone(instructor.getPhone());

        // Handle optional Studio
        if (instructor.getStudio() != null) {
            instructorEntry.setStudioId(instructor.getStudio().getId());
        }

        instructorEntry.setInstructorStatus(instructor.getStatus());

        // Convert InstructorActivityAssignment to InstructorActivityAssignmentEntry
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
        return instructorEntry;
    }

    private Instructor convertToEntity(InstructorEntry instructorEntry) {

        Instructor instructor = new Instructor();
        instructor.setId(instructorEntry.getInstructorId());
        instructor.setName(instructorEntry.getName());
        instructor.setEmail(instructorEntry.getEmail());
        instructor.setPhone(instructorEntry.getPhone());
        instructor.setProfileImage(instructorEntry.getProfileImage());
        instructor.setStatus(instructorEntry.getInstructorStatus());

        if (instructorEntry.getBankAccountDetails() != null) {
            BankAccount bankAccount = new BankAccount();
            bankAccount.setAccountNumber(instructorEntry.getBankAccountDetails().getAccountNumber());
            bankAccount.setBankName(instructorEntry.getBankAccountDetails().getBankName());
            bankAccount.setBranchName(instructorEntry.getBankAccountDetails().getBranchName());
            bankAccount.setIfscCode(instructorEntry.getBankAccountDetails().getIfscCode());
            bankAccount.setUpiId(instructorEntry.getBankAccountDetails().getUpiId());
            instructor.setBankAccount(bankAccount);
            bankAccount.setInstructor(instructor);
        }

        if (instructorEntry.getStudioId() != null) {
            StudioEntry studioEntry = studioManager.getStudioById(instructorEntry.getStudioId());
            instructor.setStudio(studioManagers.convertToEntity(studioEntry));
        }

        return instructor;
    }

}
