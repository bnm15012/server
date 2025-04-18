package com.dancestudio.erp.manager.impl;


import com.dancestudio.erp.entity.Instructor;
import com.dancestudio.erp.entry.*;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.BankAccountManager;
import com.dancestudio.erp.manager.InstructorActivityAssignmentManager;
import com.dancestudio.erp.manager.InstructorManager;
import com.dancestudio.erp.manager.StudioManager;
import com.dancestudio.erp.repository.InstructorRepository;
import com.dancestudio.erp.util.ConvertToEntryUtil;
import com.dancestudio.erp.util.DateUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InstructorManagerImpl implements InstructorManager {
    private final InstructorRepository instructorRepository;

    @Autowired
    private StudioManager studioManager;

    @Autowired
    private InstructorActivityAssignmentManager instructorActivityAssignmentManager;

    @Autowired
    private BankAccountManager bankAccountManager;

    @Autowired
    public InstructorManagerImpl(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    @Override
    public InstructorEntry add(InstructorEntry instructorEntry) throws Exception {
        if (instructorRepository.findByNameAndEmail(instructorEntry.getName(), instructorEntry.getEmail()).isPresent()) {
            throw new Exception("Instructor already exists");
        }

        Instructor instructor = convertToEntity(instructorEntry, null);
        if (Objects.nonNull(instructorEntry.getBankAccountDetails())) {
            BankAccountEntry bankAccountEntry = bankAccountManager.add(instructorEntry.getBankAccountDetails());
            instructor.setBankAccount(ConvertToEntryUtil.convertToEntity(bankAccountEntry, null));
        }

        instructor = instructorRepository.save(instructor);
        return convertToEntry(instructor);
    }

    @Override
    public InstructorEntry update(Long instructorId, InstructorEntry instructorEntry) throws Exception {
        Instructor existingInstructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new EntityNotFoundException("Instructor not found"));

        Instructor updatedInstructor = convertToEntity(instructorEntry, existingInstructor);

        Instructor instructor = convertToEntity(instructorEntry, null);
        if (Objects.nonNull(instructorEntry.getBankAccountDetails())) {
            BankAccountEntry bankAccountEntry = bankAccountManager.update(instructorEntry.getBankAccountDetails().getBankAccountId(), instructorEntry.getBankAccountDetails());
            instructor.setBankAccount(ConvertToEntryUtil.convertToEntity(bankAccountEntry, null));
        }

        updatedInstructor = instructorRepository.save(updatedInstructor);
        return convertToEntry(updatedInstructor);
    }

    @Override
    public void delete(Long instructorId) throws EntityNotFoundException {
        instructorRepository.findById(instructorId)
                .orElseThrow(() -> new EntityNotFoundException("Instructor not found"));

        instructorRepository.deleteById(instructorId);
    }

    @Override
    public InstructorEntry getById(Long instructorId) throws EntityNotFoundException {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new EntityNotFoundException("Instructor not found"));

        return convertToEntry(instructor);
    }

    @Override
    public List<InstructorEntry> getAllInstructorsByStudio(Long studioId, MembershipStatus membershipStatus, int page, int size) {
        if (size == -1) {
            List<Instructor> entries = instructorRepository.findAllByStudioIdAndOptionalActivityIdAndOptionalStatus(studioId, null, null);
            return entries.stream()
                    .map(this::convertToEntry)
                    .collect(Collectors.toList());
        } else {
            Pageable pageable = PageRequest.of(page, size);
            Page<Instructor> instructorPage = instructorRepository.findAllByStudioIdAndOptionalActivityIdAndOptionalStatus(studioId, null, membershipStatus.name(), pageable);
            return instructorPage.getContent().stream()
                    .map(this::convertToEntry)
                    .collect(Collectors.toList());
        }
    }

    private InstructorEntry convertToEntry(Instructor instructor) {
        InstructorEntry instructorEntry = new InstructorEntry();
        instructorEntry.setInstructorId(instructor.getId());
        instructorEntry.setName(instructor.getName());
        instructorEntry.setEmail(instructor.getEmail());
        instructorEntry.setPhone(instructor.getPhone());
        instructorEntry.setImageUrl(instructor.getProfileImage());

        try {
            if (instructor.getStudio() != null) {
                StudioEntry studioEntry = studioManager.getById(instructor.getStudio().getId());
                instructorEntry.setStudioEntry(studioEntry);
            }
        } catch (Exception ex) {
            instructorEntry.setStudioEntry(null);
        }

        try {
             List<InstructorActivityAssignmentEntry> entries = instructorActivityAssignmentManager.getInstructorAssignmentsByInstructorId(instructor.getId());
             boolean isActive = false;

            instructorEntry.setAssignments(entries);
            for (InstructorActivityAssignmentEntry entry : entries) {
                 if (entry.getEndDate() == null || entry.getEndDate().after(DateUtil.getCurrentDateUTC())) {
                     isActive = true;
                     break;
                 }
             }
 
            instructorEntry.setInstructorStatus(isActive ? MembershipStatus.ACTIVE : MembershipStatus.INACTIVE);
        } catch (Exception ex) {
            instructorEntry.setInstructorStatus(null);
        }

        try {
            if (instructor.getBankAccount() != null) {
                BankAccountEntry bankAccountEntry = bankAccountManager.getById(instructor.getBankAccount().getId());
                instructorEntry.setBankAccountDetails(bankAccountEntry);
            }
        } catch (Exception ex) {
            instructorEntry.setBankAccountDetails(null);
        }

        return instructorEntry;
    }

    private Instructor convertToEntity(InstructorEntry instructorEntry, Instructor existingInstructor) throws Exception {
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

        // Bank account details
        if (Objects.nonNull(instructorEntry.getBankAccountDetails()) && Objects.nonNull(instructorEntry.getBankAccountDetails().getBankAccountId())) {
            BankAccountEntry entry = bankAccountManager.getById(instructorEntry.getBankAccountDetails().getBankAccountId());
            instructor.setBankAccount(ConvertToEntryUtil.convertToEntity(entry, null));
        }

        // Studio details
        if (instructorEntry.getStudioEntry() != null && instructorEntry.getStudioEntry().getStudioId() != null) {
            StudioEntry entry = studioManager.getById(instructorEntry.getStudioEntry().getStudioId());
            instructor.setStudio(ConvertToEntryUtil.convertToEntity(entry, null));
        }

        return instructor;
    }

    @Override
    public Long getCountInstructorByStrudioId(Long studioId) {
        return instructorRepository.totalInstructorsByStudioId(studioId);
    }
}
