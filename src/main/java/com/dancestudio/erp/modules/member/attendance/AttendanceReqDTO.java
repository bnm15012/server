package com.dancestudio.erp.modules.member.attendance;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceReqDTO {
    private List<Long> activityAssignmentIds;
    private LocalDate date;
    private boolean present;
}
