package com.dancestudio.erp.modules.member;

import com.dancestudio.erp.modules.member.instructor.instructorActivityAssignment.InstructorActivityAssignment;
import com.dancestudio.erp.modules.member.instructor.instructorActivityAssignment.InstructorActivityAssignmentManager;
import com.dancestudio.erp.modules.member.student.StudentActivityAssignment.StudentActivityAssignment;
import com.dancestudio.erp.modules.member.student.StudentActivityAssignment.StudentActivityAssignmentManager;
import com.dancestudio.erp.modules.payments.PaymentConvertor;

import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.dancestudio.erp.enums.MemberType;
import com.dancestudio.erp.enums.MembershipStatus;

@Component
public class MemberManager {

    private final InstructorActivityAssignmentManager instructorActivityAssignmentManager;
    private final StudentActivityAssignmentManager studentActivityAssignmentManager;
    private final MemberRepository memberRepository;

    MemberManager(MemberRepository memberRepository,
            StudentActivityAssignmentManager studentActivityAssignmentManager,
            InstructorActivityAssignmentManager instructorActivityAssignmentManager) {
        this.memberRepository = memberRepository;
        this.studentActivityAssignmentManager = studentActivityAssignmentManager;
        this.instructorActivityAssignmentManager = instructorActivityAssignmentManager;
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public MemberEntry getMemberData(String email) {
        if (email == null) {
            throw new EntityNotFoundException("User not found");
        }
        Member member = memberRepository.findByEmail(email);
        if (Objects.isNull(member)) {
            throw new EntityNotFoundException("User not found");
        }
        MemberEntry memberEntry = convertToEntry(member);
        List<MemberActivityAssignmentEntry> memberActivityAssignmentEntries = getMemberActivityAssignmentEntries(
                member, 0, 10).getContent();
        memberEntry.setAssignments(memberActivityAssignmentEntries);
        return memberEntry;
    }

    public Page<MemberActivityAssignmentEntry> getMemberActivityAssignmentEntries(Member member, Integer page,
            Integer size) {
        List<MemberActivityAssignmentEntry> memberActivityAssignmentEntries = new ArrayList<>();
        Page<?> sourcePage = Page.empty();
        try {
            switch (MemberType.valueOf(member.getMemberType())) {
                case STUDENT:
                    Page<StudentActivityAssignment> assignmentsByStudent = studentActivityAssignmentManager
                            .getAssignmentsByStudentId(member.getId(), page, size);
                    sourcePage = assignmentsByStudent;

                    assignmentsByStudent.forEach(assignment -> {
                        try {
                            MemberActivityAssignmentEntry memberActivityAssignmentEntry = new MemberActivityAssignmentEntry();
                            memberActivityAssignmentEntry.setMemberId(assignment.getStudent().getId());
                            memberActivityAssignmentEntry.setAssignmentId(assignment.getId());
                            memberActivityAssignmentEntry.setActivityName(assignment.getActivityName());
                            memberActivityAssignmentEntry.setRegistrationDate(assignment.getRegistrationDate());
                            memberActivityAssignmentEntry.setStartDate(assignment.getMembershipStartDate());
                            memberActivityAssignmentEntry.setEndDate(assignment.getMembershipEndDate());
                            memberActivityAssignmentEntry
                                    .setMembershipStatus(getMemberShipStatus(assignment.getMembershipStartDate(),
                                            assignment.getMembershipEndDate()));
                            memberActivityAssignmentEntry.setMembershipType(assignment.getMembershipType());
                            memberActivityAssignmentEntry.setActivityAmount(assignment.getActivityAmount());
                            memberActivityAssignmentEntry.setDaysPerWeek(assignment.getDaysPerWeek());
                            memberActivityAssignmentEntry.setBatchName(assignment.getBatchName());
                            memberActivityAssignmentEntry.setBatchTime(assignment.getBatchTime());
                            memberActivityAssignmentEntry
                                    .setPaymentEntry(PaymentConvertor.convertToEntry(assignment.getPayment()));
                            memberActivityAssignmentEntries.add(memberActivityAssignmentEntry);
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    });

                    break;
                case INSTRUCTOR:
                    Page<InstructorActivityAssignment> assignmentsByInstructor = instructorActivityAssignmentManager
                            .getAssignmentsByInstructor(member.getId(), page, size);
                    sourcePage = assignmentsByInstructor;
                    assignmentsByInstructor.forEach(assignment -> {
                        try {
                            MemberActivityAssignmentEntry memberActivityAssignmentEntry = new MemberActivityAssignmentEntry();
                            memberActivityAssignmentEntry.setMemberId(assignment.getInstructor().getId());
                            memberActivityAssignmentEntry.setAssignmentId(assignment.getId());
                            memberActivityAssignmentEntry.setRegistrationDate(assignment.getAssignedDate());
                            memberActivityAssignmentEntry.setActivityName(assignment.getActivityName());
                            memberActivityAssignmentEntry.setStartDate(assignment.getStartDate());
                            memberActivityAssignmentEntry.setEndDate(assignment.getEndDate());
                            memberActivityAssignmentEntry
                                    .setMembershipStatus(getMemberShipStatus(assignment.getStartDate(),
                                            assignment.getEndDate()));

                            memberActivityAssignmentEntry.setContractDocument(assignment.getContractDocument());
                            memberActivityAssignmentEntries.add(memberActivityAssignmentEntry);
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    });
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
        }
        return new PageImpl<>(
                memberActivityAssignmentEntries,
                sourcePage.getPageable(),
                sourcePage.getTotalElements());
    }

    private MembershipStatus getMemberShipStatus(Date membershipStartDate, Date membershipEndDate) {
        Date currentDate = new Date();
        if (Objects.isNull(membershipEndDate)) {
            return MembershipStatus.ACTIVE;
        } else if (currentDate.after(membershipEndDate) || currentDate.before(membershipStartDate)) {
            return MembershipStatus.INACTIVE;
        } else {
            return MembershipStatus.ACTIVE;
        }
    }

    private MemberEntry convertToEntry(Member member) {
        MemberEntry memberEntry = new MemberEntry();
        memberEntry.setName(member.getName());
        memberEntry.setEmail(member.getEmail());
        memberEntry.setPhone(member.getPhone());
        memberEntry.setDob(member.getDob());
        memberEntry.setProfileImage(member.getProfileImage());
        memberEntry.setAddress(member.getAddress());
        memberEntry.setEmergencyContactNumber(member.getEmergencyContactNumber());
        memberEntry.setMemberType(member.getMemberType());
        memberEntry.setBranchId(member.getBranch().getId());
        return memberEntry;
    }

}
