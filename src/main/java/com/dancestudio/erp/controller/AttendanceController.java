package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.AttendanceEntry;
import com.dancestudio.erp.response.AttendanceResponse;
import com.dancestudio.erp.service.AttendanceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendance")
public class AttendanceController extends BaseController<AttendanceEntry, AttendanceResponse, Long> {

    @Autowired
    private AttendanceService attendanceService;

    @Override
    public ResponseEntity<AttendanceResponse> add(@RequestBody AttendanceEntry attendanceEntry) {
        return attendanceService.add(attendanceEntry);
    }

    @Override
    public ResponseEntity<AttendanceResponse> update(@PathVariable Long id, @RequestBody AttendanceEntry attendanceEntry) {
        return attendanceService.update(id, attendanceEntry);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return attendanceService.delete(id);
    }

    @Override
    public ResponseEntity<AttendanceResponse> get(@PathVariable Long id) {
        return attendanceService.get(id);
    }

    @GetMapping("/getAllAttendances")
    public ResponseEntity<AttendanceResponse> getAllAttendances(HttpServletRequest request) {
        return attendanceService.getAllAttendances(request);
    }
}
