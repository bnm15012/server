package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.AttendanceEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface AttendanceManager extends BaseManager<AttendanceEntry, Long> {

    List<AttendanceEntry> getAllAttendances(HttpServletRequest request) throws EntityNotFoundException;

}
