package com.dancestudio.erp.modules.member;

import java.util.Date;

import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.modules.payments.entry.PaymentEntry;

import lombok.Data;

@Data
public class MemberActivityAssignmentEntry {
    private Long memberId;
    private Long assignmentId;
    private String activityName;
    private Date registrationDate;

    private Date startDate;
    private Date endDate;
    private MembershipStatus membershipStatus;

    // student only
    private String membershipType;
    private Double activityAmount;
    private Integer daysPerWeek;
    private String batchName;
    private String batchTime;
    private PaymentEntry paymentEntry;

    // instructor only
    private String contractDocument;
}
