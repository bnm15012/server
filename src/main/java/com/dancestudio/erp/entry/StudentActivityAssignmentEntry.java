package com.dancestudio.erp.entry;

import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.enums.MembershipType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentActivityAssignmentEntry {

    private Long assignmentId;
    private ActivityEntry activity;
    private LocalDate registrationDate;
    private LocalDate membershipStartDate;
    private LocalDate membershipEndDate;
    private MembershipType membershipType;
    private MembershipStatus membershipStatus = MembershipStatus.INACTIVE;

    private Long studentId;
}
