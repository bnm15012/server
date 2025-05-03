package com.dancestudio.erp.entry;

import lombok.Data;

@Data
public class AttendanceEntry {

    private Long id;
    private Long studentId;
    private String studentName;

    private Long activityId;
    private String activityName;

    private String status;

    private Long branchId;
}
