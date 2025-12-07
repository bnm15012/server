package com.dancestudio.erp.modules.member.instructor;

import com.dancestudio.erp.enums.MembershipStatus;
import lombok.Data;

import java.util.Date;

@Data
public class InstructorActivityAssignmentEntry {

    private Long assignmentId;
    private String activityName;
    private Date assignedDate;

    private Date startDate;
    private Date endDate;
    private String contractDocument;
    private MembershipStatus membershipStatus = MembershipStatus.INACTIVE;

    private Long instructorId;
}
