package com.dancestudio.erp.modules.member.instructor.instructorActivityAssignment;

import com.dancestudio.erp.enums.MembershipStatus;
import lombok.Data;

import java.util.Date;

@Data
public class InstructorActivityAssignmentEntry {

    private Long instructorId;
    private Long assignmentId;
    private String activityName;
    private Date assignedDate;

    private Date startDate;
    private Date endDate;
    private MembershipStatus membershipStatus = MembershipStatus.INACTIVE;

    private String contractDocument;
}
