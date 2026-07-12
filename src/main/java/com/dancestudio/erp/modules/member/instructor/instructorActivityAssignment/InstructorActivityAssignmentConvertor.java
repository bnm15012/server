package com.dancestudio.erp.modules.member.instructor.instructorActivityAssignment;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.modules.member.Member;
import com.dancestudio.erp.modules.member.MemberRepository;
import com.dancestudio.erp.modules.member.memberActiveStatus.MemberActiveStatusUtil;

import jakarta.annotation.PostConstruct;

@Component
public class InstructorActivityAssignmentConvertor {

    private static ApplicationContext applicationContext;

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        applicationContext = context;
    }

    public static InstructorActivityAssignmentEntry convertToEntry(
            InstructorActivityAssignment instructorActivityAssignment) {

        if (Objects.isNull(instructorActivityAssignment)) {
            return null;
        }

        InstructorActivityAssignmentEntry instructorActivityAssignmentEntry = new InstructorActivityAssignmentEntry();
        instructorActivityAssignmentEntry.setAssignmentId(instructorActivityAssignment.getId());
        instructorActivityAssignmentEntry.setInstructorId(instructorActivityAssignment.getInstructor().getId());
        instructorActivityAssignmentEntry.setAssignedDate(instructorActivityAssignment.getAssignedDate());
        instructorActivityAssignmentEntry.setStartDate(instructorActivityAssignment.getStartDate());
        instructorActivityAssignmentEntry.setEndDate(instructorActivityAssignment.getEndDate());
        instructorActivityAssignmentEntry.setContractDocument(instructorActivityAssignment.getContractDocument());

        instructorActivityAssignmentEntry.setMembershipStatus(
                (MemberActiveStatusUtil.getMembershipStatus(instructorActivityAssignment.getStartDate(),
                        instructorActivityAssignment.getEndDate())));

        if (Objects.nonNull(instructorActivityAssignment.getActivityName())) {
            instructorActivityAssignmentEntry.setActivityName(instructorActivityAssignment.getActivityName());
        }
        return instructorActivityAssignmentEntry;
    }

    public static InstructorActivityAssignment convertToEntity(
            InstructorActivityAssignmentEntry instructorActivityAssignmentEntry,
            InstructorActivityAssignment existingInstructorActivityAssignment) throws EntityNotFoundException {
        InstructorActivityAssignment instructorActivityAssignment = (existingInstructorActivityAssignment != null)
                ? existingInstructorActivityAssignment
                : new InstructorActivityAssignment();

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
        if (Objects.nonNull(instructorActivityAssignmentEntry.getContractDocument())) {
            instructorActivityAssignment.setContractDocument(instructorActivityAssignmentEntry.getContractDocument());
        }
        if (Objects.nonNull(instructorActivityAssignmentEntry.getInstructorId())) {
            MemberRepository memberRepository = applicationContext.getBean(MemberRepository.class);
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
