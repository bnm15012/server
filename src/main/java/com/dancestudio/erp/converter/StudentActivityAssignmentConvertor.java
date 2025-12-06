package com.dancestudio.erp.converter;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.dancestudio.erp.entity.Member;
import com.dancestudio.erp.entity.StudentActivityAssignment;
import com.dancestudio.erp.entry.StudentActivityAssignmentEntry;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.modules.payments.PaymentConvertor;
import com.dancestudio.erp.repository.MemberRepository;
import com.dancestudio.erp.util.DateUtil;

import jakarta.annotation.PostConstruct;

@Component
public class StudentActivityAssignmentConvertor {

    private static ApplicationContext applicationContext;

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        applicationContext = context;
    }

    public static StudentActivityAssignmentEntry convertToEntry(StudentActivityAssignment studentActivityAssignment)
            throws Exception {

        if (Objects.isNull(studentActivityAssignment)) {
            return null;
        }

        StudentActivityAssignmentEntry studentActivityAssignmentEntry = new StudentActivityAssignmentEntry();
        studentActivityAssignmentEntry.setAssignmentId(studentActivityAssignment.getId());
        studentActivityAssignmentEntry.setStudentId(studentActivityAssignment.getStudent().getId());
        studentActivityAssignmentEntry.setRegistrationDate(studentActivityAssignment.getRegistrationDate());
        studentActivityAssignmentEntry.setBatchName(studentActivityAssignment.getBatchName());
        studentActivityAssignmentEntry.setBatchTime(studentActivityAssignment.getBatchTime());
        studentActivityAssignmentEntry.setMembershipStartDate(studentActivityAssignment.getMembershipStartDate());
        studentActivityAssignmentEntry.setMembershipEndDate(studentActivityAssignment.getMembershipEndDate());
        studentActivityAssignmentEntry.setDaysPerWeek(studentActivityAssignment.getDaysPerWeek());
        studentActivityAssignmentEntry.setMembershipStatus(
                studentActivityAssignment.getMembershipEndDate().after(DateUtil.getCurrentDateUTC())
                        ? MembershipStatus.ACTIVE
                        : MembershipStatus.INACTIVE);
        studentActivityAssignmentEntry
                .setMembershipType((studentActivityAssignment.getMembershipType()));
        studentActivityAssignmentEntry.setActivityAmount(studentActivityAssignment.getActivityAmount());

        if (Objects.nonNull(studentActivityAssignment.getActivityName())) {
            studentActivityAssignmentEntry.setActivityName(studentActivityAssignment.getActivityName());
        }
        if (Objects.nonNull(studentActivityAssignment.getPayment())) {
            studentActivityAssignmentEntry
                    .setPaymentEntry(PaymentConvertor.convertToEntry(studentActivityAssignment.getPayment()));
        }
        return studentActivityAssignmentEntry;
    }

    public static StudentActivityAssignment convertToEntity(
            StudentActivityAssignmentEntry studentActivityAssignmentEntry,
            StudentActivityAssignment existingStudentActivityAssignment) throws Exception {
        StudentActivityAssignment studentActivityAssignment = (existingStudentActivityAssignment != null)
                ? existingStudentActivityAssignment
                : new StudentActivityAssignment();

        if (Objects.nonNull(studentActivityAssignmentEntry.getAssignmentId())) {
            studentActivityAssignment.setId(studentActivityAssignmentEntry.getAssignmentId());
        }
        if (Objects.nonNull(studentActivityAssignmentEntry.getRegistrationDate())) {
            studentActivityAssignment.setRegistrationDate(studentActivityAssignmentEntry.getRegistrationDate());
        }
        if (Objects.nonNull(studentActivityAssignmentEntry.getBatchName())) {
            studentActivityAssignment.setBatchName(studentActivityAssignmentEntry.getBatchName());
        }
        if (Objects.nonNull(studentActivityAssignmentEntry.getBatchTime())) {
            studentActivityAssignment.setBatchTime(studentActivityAssignmentEntry.getBatchTime());
        }
        if (Objects.nonNull(studentActivityAssignmentEntry.getMembershipStartDate())) {
            studentActivityAssignment.setMembershipStartDate(studentActivityAssignmentEntry.getMembershipStartDate());
        }
        if (Objects.nonNull(studentActivityAssignmentEntry.getMembershipEndDate())) {
            studentActivityAssignment.setMembershipEndDate(studentActivityAssignmentEntry.getMembershipEndDate());
        }
        if (Objects.nonNull(studentActivityAssignmentEntry.getMembershipType())) {
            studentActivityAssignment.setMembershipType(studentActivityAssignmentEntry.getMembershipType());
        }
        if (Objects.nonNull(studentActivityAssignmentEntry.getActivityAmount())) {
            studentActivityAssignment.setActivityAmount(studentActivityAssignmentEntry.getActivityAmount());
        }
        if (Objects.nonNull(studentActivityAssignmentEntry.getDaysPerWeek())) {
            studentActivityAssignment.setDaysPerWeek(studentActivityAssignmentEntry.getDaysPerWeek());
        }
        if (Objects.nonNull(studentActivityAssignmentEntry.getStudentId())) {
            MemberRepository memberRepository = applicationContext.getBean(MemberRepository.class);
            Member student = memberRepository.findById(studentActivityAssignmentEntry.getStudentId())
                    .orElseThrow(() -> new EntityNotFoundException("Student not found"));
            studentActivityAssignment.setStudent(student);
        }

        if (Objects.nonNull(studentActivityAssignmentEntry.getActivityName())) {
            studentActivityAssignment.setActivityName(studentActivityAssignmentEntry.getActivityName());
        }

        return studentActivityAssignment;
    }

}
