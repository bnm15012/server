package com.dancestudio.erp.entry;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InstructorActivityAssignmentEntry {

    private Long assignmentId;
    private ActivityEntry activity;
    private LocalDate assignedDate;
}
