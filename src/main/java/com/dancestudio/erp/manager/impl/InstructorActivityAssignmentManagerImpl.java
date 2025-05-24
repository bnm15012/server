package com.dancestudio.erp.manager.impl;


import com.dancestudio.erp.entity.InstructorActivityAssignment;
import com.dancestudio.erp.entity.Member;
import com.dancestudio.erp.entry.InstructorActivityAssignmentEntry;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.ActivityManager;
import com.dancestudio.erp.manager.InstructorActivityAssignmentManager;
import com.dancestudio.erp.repository.InstructorActivityAssignmentRepository;
import com.dancestudio.erp.repository.MemberRepository;
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
    private MemberRepository memberRepository;

    @Autowired
    public InstructorActivityAssignmentManagerImpl(InstructorActivityAssignmentRepository instructorActivityAssignmentRepository) {
        this.instructorActivityAssignmentRepository = instructorActivityAssignmentRepository;
    }

    @Override
    public InstructorActivityAssignmentEntry add(InstructorActivityAssignmentEntry instructorActivityAssignmentEntry) throws Exception {
        memberRepository.findById(instructorActivityAssignmentEntry.getInstructorId())
                .orElseThrow(() -> new EntityNotFoundException("Instructor not found"));

        InstructorActivityAssignment instructorActivityAssignment = convertToEntity(instructorActivityAssignmentEntry, null);
        instructorActivityAssignment = instructorActivityAssignmentRepository.save(instructorActivityAssignment);

        return convertToEntry(instructorActivityAssignment);
    }

    @Override
    public InstructorActivityAssignmentEntry update(Long instructorActivityAssignmentId, InstructorActivityAssignmentEntry instructorActivityAssignmentEntry) throws Exception {
        InstructorActivityAssignment existingInstructorActivityAssignment = instructorActivityAssignmentRepository.findById(instructorActivityAssignmentId)
                .orElseThrow(() -> new EntityNotFoundException("InstructorActivityAssignment not found"));

        InstructorActivityAssignment updatedInstructorActivityAssignment = convertToEntity(instructorActivityAssignmentEntry, existingInstructorActivityAssignment);
        updatedInstructorActivityAssignment = instructorActivityAssignmentRepository.save(updatedInstructorActivityAssignment);

        return convertToEntry(instructorActivityAssignmentRepository.save(updatedInstructorActivityAssignment));
    }

    @Override
    public void delete(Long instructorActivityAssignmentId) throws EntityNotFoundException {
        instructorActivityAssignmentRepository.findById(instructorActivityAssignmentId)
                .orElseThrow(() -> new EntityNotFoundException("InstructorActivityAssignment not found"));

        instructorActivityAssignmentRepository.deleteById(instructorActivityAssignmentId);
    }

    @Override
    public InstructorActivityAssignmentEntry getById(Long instructorActivityAssignmentAssignmentId) throws Exception {
        InstructorActivityAssignment instructorActivityAssignment = instructorActivityAssignmentRepository.findById(instructorActivityAssignmentAssignmentId)
                .orElseThrow(() -> new EntityNotFoundException("InstructorActivityAssignment not found"));

        return convertToEntry(instructorActivityAssignment);
    }

    @Override
    public InstructorActivityAssignmentEntry getInstructorAssignmentsByInstructorAndActivityId(Long instructorId, String activityName) throws Exception {
        InstructorActivityAssignment assignment = instructorActivityAssignmentRepository.findByInstructorIdAndActivityId(instructorId, activityName);
        return convertToEntry(assignment);
    }

    @Override
    public List<InstructorActivityAssignmentEntry> getInstructorAssignmentsByInstructorId(Long instructorId) throws Exception {
        List<InstructorActivityAssignment> assignments = instructorActivityAssignmentRepository.findByInstructorId(instructorId);
        List<InstructorActivityAssignmentEntry> entries = new ArrayList<>();

        for (InstructorActivityAssignment assignment : assignments) {
            entries.add(convertToEntry(assignment));
        }
        return entries;
    }

    private InstructorActivityAssignmentEntry convertToEntry(InstructorActivityAssignment instructorActivityAssignment) throws Exception {

        if(Objects.isNull(instructorActivityAssignment)) {
            return null;
        }

        InstructorActivityAssignmentEntry instructorActivityAssignmentEntry = new InstructorActivityAssignmentEntry();
        instructorActivityAssignmentEntry.setAssignmentId(instructorActivityAssignment.getId());
        instructorActivityAssignmentEntry.setInstructorId(instructorActivityAssignment.getInstructor().getId());
        instructorActivityAssignmentEntry.setAssignedDate(instructorActivityAssignment.getAssignedDate());
        instructorActivityAssignmentEntry.setStartDate(instructorActivityAssignment.getStartDate());
        instructorActivityAssignmentEntry.setEndDate(instructorActivityAssignment.getEndDate());

        instructorActivityAssignmentEntry.setMembershipStatus(
                (instructorActivityAssignment.getEndDate() == null || instructorActivityAssignment.getEndDate().after(DateUtil.getCurrentDateUTC()))
                        ? MembershipStatus.ACTIVE : MembershipStatus.INACTIVE
        );

        if (Objects.nonNull(instructorActivityAssignment.getActivityName())) {
            instructorActivityAssignmentEntry.setActivityName(instructorActivityAssignment.getActivityName());
        }
        return instructorActivityAssignmentEntry;
    }

    private InstructorActivityAssignment convertToEntity(InstructorActivityAssignmentEntry instructorActivityAssignmentEntry, InstructorActivityAssignment existingInstructorActivityAssignment) throws Exception {
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
            Member instructor = memberRepository.findById(instructorActivityAssignmentEntry.getInstructorId())
                    .orElseThrow(() -> new EntityNotFoundException("Student not found"));
            instructorActivityAssignment.setInstructor(instructor);
        }
        if (Objects.nonNull(instructorActivityAssignmentEntry.getActivityName())) {
            instructorActivityAssignment.setActivityName(instructorActivityAssignmentEntry.getActivityName());
        }

        return instructorActivityAssignment;
    }
}
