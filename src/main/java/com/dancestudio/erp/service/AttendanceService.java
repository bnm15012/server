package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.AttendanceEntry;
import com.dancestudio.erp.response.AttendanceResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface AttendanceService extends BaseService<AttendanceEntry, AttendanceResponse, Long> {

    ResponseEntity<AttendanceResponse> getAllAttendances(HttpServletRequest request);
}
