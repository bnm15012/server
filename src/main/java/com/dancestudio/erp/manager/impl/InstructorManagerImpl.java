package com.dancestudio.erp.manager.impl;


import com.dancestudio.erp.entity.Instructor;
import com.dancestudio.erp.entry.BankAccountEntry;
import com.dancestudio.erp.entry.InstructorEntry;
import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.BankAccountManager;
import com.dancestudio.erp.manager.InstructorManager;
import com.dancestudio.erp.manager.StudioManager;
import com.dancestudio.erp.repository.InstructorRepository;
import com.dancestudio.erp.util.ConvertToEntryUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class InstructorManagerImpl implements InstructorManager {
    private final InstructorRepository instructorRepository;

    @Autowired
    private StudioManager studioManager;

    @Autowired
    private BankAccountManager bankAccountManager;

    @Autowired
    public InstructorManagerImpl(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    @Override
    public InstructorEntry addInstructor(InstructorEntry instructorEntry) throws Exception {
        if (instructorRepository.findByNameAndEmail(instructorEntry.getName(), instructorEntry.getEmail()).isPresent()) {
            throw new Exception("Instructor already exists");
        }

        Instructor instructor = convertToEntity(instructorEntry, null);
        if(Objects.nonNull(instructorEntry.getBankAccountDetails())) {
            BankAccountEntry bankAccountEntry = bankAccountManager.addBankAccount(instructorEntry.getBankAccountDetails());
            instructor.setBankAccount(ConvertToEntryUtil.convertToEntity(bankAccountEntry, null));
        }

        instructor = instructorRepository.save(instructor);
        return convertToEntry(instructor);
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
        instructorRepository.findById(instructorId)
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
    public List<InstructorEntry> getAllInstructorsByStudio(Long studioId, MembershipStatus membershipStatus) throws EntityNotFoundException {
        List<Instructor> entries = instructorRepository.findAllByStudioId(studioId, membershipStatus);

        List<InstructorEntry> instructorEntries = new ArrayList<>();
        for (Instructor entry : entries) {
            InstructorEntry instructorEntry = convertToEntry(entry);
            instructorEntries.add(instructorEntry);
        }

        return instructorEntries;
    }

    private InstructorEntry convertToEntry(Instructor instructor) throws EntityNotFoundException {
        InstructorEntry instructorEntry = new InstructorEntry();
        instructorEntry.setInstructorId(instructor.getId());
        instructorEntry.setName(instructor.getName());
        instructorEntry.setEmail(instructor.getEmail());
        instructorEntry.setPhone(instructor.getPhone());
        instructorEntry.setImageUrl(instructor.getProfileImage());

        // Handle optional Studio
        if (instructor.getStudio() != null) {
            StudioEntry studioEntry = studioManager.getStudioById(instructor.getStudio().getId());
            instructorEntry.setStudioEntry(studioEntry);
        }

        instructorEntry.setInstructorStatus(MembershipStatus.valueOf(instructor.getStatus()));

        // Convert Bank Account details if available
        if (instructor.getBankAccount() != null) {
            BankAccountEntry bankAccountEntry = bankAccountManager.getBankAccountById(instructor.getBankAccount().getId());
            instructorEntry.setBankAccountDetails(bankAccountEntry);
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
            instructor.setStatus(instructorEntry.getInstructorStatus().name());
        }

        // Bank account details
        if (Objects.nonNull(instructorEntry.getBankAccountDetails()) && Objects.nonNull(instructorEntry.getBankAccountDetails().getBankAccountId())) {
            BankAccountEntry entry = bankAccountManager.getBankAccountById(instructorEntry.getBankAccountDetails().getBankAccountId());
            instructor.setBankAccount(ConvertToEntryUtil.convertToEntity(entry, null));
        }

        // Studio details
        if (instructorEntry.getStudioEntry() != null && instructorEntry.getStudioEntry().getStudioId() != null) {
            StudioEntry entry = studioManager.getStudioById(instructorEntry.getStudioEntry().getStudioId());
            instructor.setStudio(ConvertToEntryUtil.convertToEntity(entry, null));
        }

        return instructor;
    }

}
