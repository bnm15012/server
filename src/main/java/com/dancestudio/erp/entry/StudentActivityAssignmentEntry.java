package com.dancestudio.erp.entry;

import com.dancestudio.erp.enums.ActivityType;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.enums.MembershipType;
import lombok.Data;

import java.util.Date;

@Data
public class StudentActivityAssignmentEntry {

    private Long assignmentId;
    private ActivityEntry activity;
    private Date registrationDate;
    private Date membershipStartDate;
    private Date membershipEndDate;
    private MembershipType membershipType;
    private Double activityAmount;
    private MembershipStatus membershipStatus = MembershipStatus.INACTIVE;

    private Long studentId;
}
