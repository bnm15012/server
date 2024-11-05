package com.dancestudio.erp.entry;

import com.dancestudio.erp.enums.MembershipStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InstructorActivityAssignmentEntry {

    private Long assignmentId;
    private ActivityEntry activity;
    private LocalDate assignedDate;

    private LocalDate startDate;
    private LocalDate endDate;
    private MembershipStatus membershipStatus = MembershipStatus.INACTIVE;

    private Long instructorId;
}
