package com.dancestudio.erp.manager.impl;


import com.dancestudio.erp.entity.Instructor;
import com.dancestudio.erp.entity.InstructorActivityAssignment;
import com.dancestudio.erp.entry.ActivityEntry;
import com.dancestudio.erp.entry.InstructorActivityAssignmentEntry;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.ActivityManager;
import com.dancestudio.erp.manager.InstructorActivityAssignmentManager;
import com.dancestudio.erp.repository.InstructorActivityAssignmentRepository;
import com.dancestudio.erp.repository.InstructorRepository;
import com.dancestudio.erp.util.ConvertToEntryUtil;
import com.dancestudio.erp.util.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class InstructorActivityAssignmentManagerImpl implements InstructorActivityAssignmentManager {
    private final InstructorActivityAssignmentRepository instructorActivityAssignmentRepository;

    @Autowired
    private ActivityManager activityManager;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    public InstructorActivityAssignmentManagerImpl(InstructorActivityAssignmentRepository instructorActivityAssignmentRepository) {
        this.instructorActivityAssignmentRepository = instructorActivityAssignmentRepository;
    }

    @Override
    public InstructorActivityAssignmentEntry addInstructorActivityAssignment(InstructorActivityAssignmentEntry instructorActivityAssignmentEntry) throws EntityNotFoundException {
        instructorRepository.findById(instructorActivityAssignmentEntry.getInstructorId())
                .orElseThrow(() -> new EntityNotFoundException("Instructor not found"));

        InstructorActivityAssignment instructorActivityAssignment = convertToEntity(instructorActivityAssignmentEntry, null);
        instructorActivityAssignment = instructorActivityAssignmentRepository.save(instructorActivityAssignment);

        return convertToEntry(instructorActivityAssignment);
    }

    @Override
    public InstructorActivityAssignmentEntry updateInstructorActivityAssignment(Long instructorActivityAssignmentId, InstructorActivityAssignmentEntry instructorActivityAssignmentEntry) throws EntityNotFoundException {
        InstructorActivityAssignment existingInstructorActivityAssignment = instructorActivityAssignmentRepository.findById(instructorActivityAssignmentId)
                .orElseThrow(() -> new EntityNotFoundException("InstructorActivityAssignment not found"));

        InstructorActivityAssignment updatedInstructorActivityAssignment = convertToEntity(instructorActivityAssignmentEntry, existingInstructorActivityAssignment);
        updatedInstructorActivityAssignment = instructorActivityAssignmentRepository.save(updatedInstructorActivityAssignment);

        return convertToEntry(instructorActivityAssignmentRepository.save(updatedInstructorActivityAssignment));
    }

    @Override
    public void deleteInstructorActivityAssignment(Long instructorActivityAssignmentId) throws EntityNotFoundException {
        instructorActivityAssignmentRepository.findById(instructorActivityAssignmentId)
                .orElseThrow(() -> new EntityNotFoundException("InstructorActivityAssignment not found"));

        instructorActivityAssignmentRepository.deleteById(instructorActivityAssignmentId);
    }

    @Override
    public InstructorActivityAssignmentEntry getInstructorActivityAssignmentById(Long instructorActivityAssignmentAssignmentId) throws EntityNotFoundException {
        InstructorActivityAssignment instructorActivityAssignment = instructorActivityAssignmentRepository.findById(instructorActivityAssignmentAssignmentId)
                .orElseThrow(() -> new EntityNotFoundException("InstructorActivityAssignment not found"));

        return convertToEntry(instructorActivityAssignment);
    }

    @Override
    public InstructorActivityAssignmentEntry getInstructorAssignmentsByInstructorAndActivityId(Long instructorId, Long activityId) throws EntityNotFoundException {
        InstructorActivityAssignment assignment = instructorActivityAssignmentRepository.findByInstructorIdAndActivityId(instructorId, activityId);
        return convertToEntry(assignment);
    }

    @Override
    public List<InstructorActivityAssignmentEntry> getInstructorAssignmentsByInstructorId(Long instructorId) throws EntityNotFoundException {
        List<InstructorActivityAssignment> assignments = instructorActivityAssignmentRepository.findByInstructorId(instructorId);
        List<InstructorActivityAssignmentEntry> entries = new ArrayList<>();

        for (InstructorActivityAssignment assignment : assignments) {
            entries.add(convertToEntry(assignment));
        }
        return entries;
    }

    private InstructorActivityAssignmentEntry convertToEntry(InstructorActivityAssignment instructorActivityAssignment) throws EntityNotFoundException {

        if(Objects.isNull(instructorActivityAssignment)) {
            return null;
        }

        InstructorActivityAssignmentEntry instructorActivityAssignmentEntry = new InstructorActivityAssignmentEntry();
        instructorActivityAssignmentEntry.setAssignmentId(instructorActivityAssignment.getId());
        instructorActivityAssignmentEntry.setInstructorId(instructorActivityAssignment.getInstructor().getId());
        instructorActivityAssignmentEntry.setAssignedDate(instructorActivityAssignment.getAssignedDate());
        instructorActivityAssignmentEntry.setStartDate(instructorActivityAssignment.getStartDate());
        instructorActivityAssignmentEntry.setEndDate(instructorActivityAssignment.getEndDate());
        instructorActivityAssignmentEntry.setMembershipStatus(instructorActivityAssignment.getEndDate().after(DateUtil.getCurrentDateUTC()) ? MembershipStatus.ACTIVE : MembershipStatus.INACTIVE);

        if (Objects.nonNull(instructorActivityAssignment.getActivity())) {
            ActivityEntry activityEntry = activityManager.getActivityById(instructorActivityAssignment.getActivity().getId());
            instructorActivityAssignmentEntry.setActivity(activityEntry);
        }
        return instructorActivityAssignmentEntry;
    }

    private InstructorActivityAssignment convertToEntity(InstructorActivityAssignmentEntry instructorActivityAssignmentEntry, InstructorActivityAssignment existingInstructorActivityAssignment) throws EntityNotFoundException {
        InstructorActivityAssignment instructorActivityAssignment = (existingInstructorActivityAssignment != null) ? existingInstructorActivityAssignment : new InstructorActivityAssignment();

        if (Objects.nonNull(instructorActivityAssignmentEntry.getAssignmentId())) {
            instructorActivityAssignment.setId(instructorActivityAssignmentEntry.getAssignmentId());
        }
        if (Objects.nonNull(instructorActivityAssignmentEntry.getAssignedDate())) {
            instructorActivityAssignment.setAssignedDate(instructorActivityAssignmentEntry.getAssignedDate());
        }
        if (Objects.nonNull(instructorActivityAssignmentEntry.getStartDate())) {
            instructorActivityAssignment.setStartDate(instructorActivityAssignmentEntry.getStartDate());
        }
        if (Objects.nonNull(instructorActivityAssignmentEntry.getEndDate())) {
            instructorActivityAssignment.setEndDate(instructorActivityAssignmentEntry.getEndDate());
        }
        if (Objects.nonNull(instructorActivityAssignmentEntry.getInstructorId())) {
            Instructor instructor = instructorRepository.findById(instructorActivityAssignmentEntry.getInstructorId())
                    .orElseThrow(() -> new EntityNotFoundException("Student not found"));
            instructorActivityAssignment.setInstructor(instructor);
        }

        if (Objects.nonNull(instructorActivityAssignmentEntry.getActivity()) && Objects.nonNull(instructorActivityAssignmentEntry.getActivity().getActivityId())) {
            ActivityEntry activityEntry = activityManager.getActivityById(instructorActivityAssignmentEntry.getActivity().getActivityId());
            instructorActivityAssignment.setActivity(ConvertToEntryUtil.convertToEntity(activityEntry, null));
        }

        return instructorActivityAssignment;
    }
}
