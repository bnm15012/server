package com.dancestudio.erp.entry;

import lombok.Data;

@Data
public class InstructorActivityAssignmentDTO {

    private Long assignmentId;
    private Long instructorId;
    private Long activityId;
    private Long studioId;

}
