package com.dancestudio.erp.modules.member.student;

import com.dancestudio.erp.controller.BaseController;
import com.dancestudio.erp.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/studentActivities")
public class StudentActivityAssignmentController
        extends BaseController<StudentActivityAssignmentEntry, StudentActivityAssignmentResponse, Long> {

    @Autowired
    private StudentActivityAssignmentService studentActivityAssignmentService;

    @GetMapping("/getAll/{studentId}")
    public ResponseEntity<StudentActivityAssignmentResponse> getAllInstructors(
            @PathVariable Long studentId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return studentActivityAssignmentService.getAll(studentId, page, size);
    }

    @Override
    protected BaseService<StudentActivityAssignmentEntry, StudentActivityAssignmentResponse, Long> getService() {
        return studentActivityAssignmentService;
    }
}
