package com.dancestudio.erp.modules.member.student.StudentActivityAssignment;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dancestudio.erp.base.BaseController;
import com.dancestudio.erp.base.BaseService;
import com.dancestudio.erp.modules.member.attendance.AttendanceReqDTO;

@RestController
@RequestMapping("/studentActivities")
public class StudentActivityAssignmentController
        extends BaseController<StudentActivityAssignmentEntry, Long> {

    @Autowired
    private StudentActivityAssignmentService studentActivityAssignmentService;

    @GetMapping("/getAll/{rootId}")
    public ResponseEntity<StudentActivityAssignmentResponse> getAssignments(
            @PathVariable Long rootId,
            @RequestParam(defaultValue = "STUDENT") String rootType,
            @RequestParam(required = false) com.dancestudio.erp.enums.ActivityType activityName,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return studentActivityAssignmentService.getAssignments(
                rootId, rootType, activityName, searchTerm, date, page, size);
    }

    @Override
    protected BaseService<StudentActivityAssignmentEntry, Long> getService() {
        return studentActivityAssignmentService;
    }

    @PutMapping("/mark_attendance/{activityAssignmentId}")
    public ResponseEntity<StudentActivityAssignmentResponse> markAttendance(@PathVariable Long activityAssignmentId) {
        return studentActivityAssignmentService.markAttendance(activityAssignmentId);
    }

    @PutMapping("/mark_attendance/bulk")
    public ResponseEntity<StudentActivityAssignmentResponse> markAttendanceBulk(@RequestBody AttendanceReqDTO reqDTO) {
        return studentActivityAssignmentService.markAttendanceBulk(reqDTO);
    }
}
