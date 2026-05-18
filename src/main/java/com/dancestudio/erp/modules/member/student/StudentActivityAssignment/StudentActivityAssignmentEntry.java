package com.dancestudio.erp.modules.member.student.StudentActivityAssignment;

import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.modules.member.attendance.AttendanceEntry;
import com.dancestudio.erp.modules.payments.entry.PaymentEntry;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class StudentActivityAssignmentEntry {

    private Long assignmentId;
    private String studentName;
    private String activityName;
    private Date registrationDate;
    private Date membershipStartDate;
    private Date membershipEndDate;
    private String membershipType;
    private Double activityAmount;
    private MembershipStatus membershipStatus;
    private Integer daysPerWeek;
    private String batchName;
    private String batchTime;
    private List<AttendanceEntry> attendanceEntries;

    private PaymentEntry paymentEntry;
    private Long studentId;
}
