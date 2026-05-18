package com.dancestudio.erp.modules.member.student.StudentActivityAssignment;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.dancestudio.erp.enums.ActivityType;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.modules.member.Member;
import com.dancestudio.erp.modules.member.MemberRepository;
import com.dancestudio.erp.modules.member.attendance.AttendanceEntryConverter;
import com.dancestudio.erp.modules.payments.PaymentConvertor;
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

    public static StudentActivityAssignmentEntry convertToEntry(StudentActivityAssignment studentActivityAssignment,
            String[] fields) {

        if (Objects.isNull(studentActivityAssignment)) {
            return null;
        }
        boolean includeAll = fields.length == 0;

        StudentActivityAssignmentEntry studentActivityAssignmentEntry = new StudentActivityAssignmentEntry();
        studentActivityAssignmentEntry.setAssignmentId(studentActivityAssignment.getId());

        if (includeAll || Arrays.asList(fields).contains("studentName")) {
            studentActivityAssignmentEntry.setStudentName(studentActivityAssignment.getStudent().getName());
        }
        if (includeAll || Arrays.asList(fields).contains("studentId")) {
            studentActivityAssignmentEntry.setStudentId(studentActivityAssignment.getStudent().getId());
        }
        if (includeAll || Arrays.asList(fields).contains("registrationDate")) {
            studentActivityAssignmentEntry.setRegistrationDate(studentActivityAssignment.getRegistrationDate());
        }
        if (includeAll || Arrays.asList(fields).contains("batchName")) {
            studentActivityAssignmentEntry.setBatchName(studentActivityAssignment.getBatchName());
        }
        if (includeAll || Arrays.asList(fields).contains("batchTime")) {
            studentActivityAssignmentEntry.setBatchTime(studentActivityAssignment.getBatchTime());
        }
        if (includeAll || Arrays.asList(fields).contains("membershipStartDate")) {
            studentActivityAssignmentEntry.setMembershipStartDate(studentActivityAssignment.getMembershipStartDate());
        }
        if (includeAll || Arrays.asList(fields).contains("membershipEndDate")) {
            studentActivityAssignmentEntry.setMembershipEndDate(studentActivityAssignment.getMembershipEndDate());
        }
        if (includeAll || Arrays.asList(fields).contains("daysPerWeek")) {
            studentActivityAssignmentEntry.setDaysPerWeek(studentActivityAssignment.getDaysPerWeek());
        }
        if (includeAll || Arrays.asList(fields).contains("membershipStatus")) {
            studentActivityAssignmentEntry.setMembershipStatus(
                    (!studentActivityAssignment.getMembershipStartDate().after(DateUtil.getCurrentDateUTC()) &&
                            !studentActivityAssignment.getMembershipEndDate().before(DateUtil.getCurrentDateUTC()))
                                    ? MembershipStatus.ACTIVE
                                    : MembershipStatus.INACTIVE);
        }
        if (includeAll || Arrays.asList(fields).contains("membershipType")) {
            studentActivityAssignmentEntry.setMembershipType((studentActivityAssignment.getMembershipType()));
        }
        if (includeAll || Arrays.asList(fields).contains("activityAmount")) {
            studentActivityAssignmentEntry.setActivityAmount(studentActivityAssignment.getActivityAmount());
        }
        if (includeAll || Arrays.asList(fields).contains("attendanceEntries")) {
            if (Objects.nonNull(studentActivityAssignment.getAttendanceBitmap())
                    && Objects.nonNull(studentActivityAssignment.getMembershipStartDate())) {
                studentActivityAssignmentEntry.setAttendanceEntries(AttendanceEntryConverter.bitmapToEntries(
                        studentActivityAssignment.getAttendanceBitmap(),
                        studentActivityAssignment.getMembershipStartDate()));
            }
        }
        if (includeAll || Arrays.asList(fields).contains("activityName")) {
            if (Objects.nonNull(studentActivityAssignment.getActivityName())) {
                studentActivityAssignmentEntry
                        .setActivityName(studentActivityAssignment.getActivityName().name());
            }
        }
        if (includeAll || Arrays.asList(fields).contains("payment")) {
            if (Objects.nonNull(studentActivityAssignment.getPayment())) {
                studentActivityAssignmentEntry
                        .setPaymentEntry(PaymentConvertor.convertToEntry(studentActivityAssignment.getPayment()));
            }
        }

        return studentActivityAssignmentEntry;
    }

    public static StudentActivityAssignment convertToEntity(
            StudentActivityAssignmentEntry studentActivityAssignmentEntry,
            StudentActivityAssignment existingStudentActivityAssignment) throws EntityNotFoundException {
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

        if (Objects.nonNull(studentActivityAssignmentEntry.getAttendanceEntries())) {
            studentActivityAssignment.setAttendanceBitmap(AttendanceEntryConverter.entriesToBitmap(
                    studentActivityAssignmentEntry.getAttendanceEntries(),
                    studentActivityAssignment.getMembershipStartDate()));
        }

        if (Objects.nonNull(studentActivityAssignmentEntry.getActivityName())) {
            studentActivityAssignment
                    .setActivityName(ActivityType.valueOf(studentActivityAssignmentEntry.getActivityName()));
        }

        return studentActivityAssignment;
    }

}
