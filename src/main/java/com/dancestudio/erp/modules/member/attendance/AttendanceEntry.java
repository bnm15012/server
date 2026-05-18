package com.dancestudio.erp.modules.member.attendance;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttendanceEntry {
    private Date date;
    private boolean present;
}
