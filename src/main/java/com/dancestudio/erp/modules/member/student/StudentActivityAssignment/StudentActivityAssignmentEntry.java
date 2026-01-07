package com.dancestudio.erp.modules.member.student.StudentActivityAssignment;

import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.modules.payments.entry.PaymentEntry;

import lombok.Data;

import java.util.Date;

@Data
public class StudentActivityAssignmentEntry {

    private Long assignmentId;
    private String activityName;
    private Date registrationDate;
    private Date membershipStartDate;
    private Date membershipEndDate;
    private String membershipType;
    private Double activityAmount;
    private MembershipStatus membershipStatus = MembershipStatus.INACTIVE;
    private Integer daysPerWeek;
    private String batchName;
    private String batchTime;

    private PaymentEntry paymentEntry;
    private Long studentId;
}
