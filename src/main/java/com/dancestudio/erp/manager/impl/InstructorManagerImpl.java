package com.dancestudio.erp.manager.impl;


import com.dancestudio.erp.entity.BankAccount;
import com.dancestudio.erp.entity.Instructor;
import com.dancestudio.erp.entry.InstructorEntry;
import com.dancestudio.erp.entry.StudioEntry;
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
    public List<InstructorEntry> getAllInstructors() {
        List<Instructor> entries = instructorRepository.findAll().stream().collect(Collectors.toList());

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
        instructorEntry.setStudioId(instructor.getStudio().getId());
        instructorEntry.setProfileDetails(instructor.getProfileDetails());

        return instructorEntry;
    }

    private Instructor convertToEntity(InstructorEntry instructorEntry) {

        Instructor instructor = new Instructor();
        instructor.setId(instructorEntry.getInstructorId());
        instructor.setName(instructorEntry.getName());
        instructor.setEmail(instructorEntry.getEmail());
        instructor.setPhone(instructorEntry.getPhone());
        instructor.setProfileDetails(instructorEntry.getProfileDetails());

        if (instructorEntry.getBankAccountDetails() != null) {
            BankAccount bankAccount = new BankAccount();
            bankAccount.setAccountNumber(instructorEntry.getBankAccountDetails().getAccountNumber());
            bankAccount.setBankName(instructorEntry.getBankAccountDetails().getBankName());
            bankAccount.setBranchName(instructorEntry.getBankAccountDetails().getBranchName());
            bankAccount.setIfscCode(instructorEntry.getBankAccountDetails().getIfscCode());

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
