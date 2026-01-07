package com.dancestudio.erp.modules.member.student.StudentActivityAssignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dancestudio.erp.base.BaseController;
import com.dancestudio.erp.base.BaseService;

@RestController
@RequestMapping("/studentActivities")
public class StudentActivityAssignmentController
        extends BaseController<StudentActivityAssignmentEntry, Long> {

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
    protected BaseService<StudentActivityAssignmentEntry, Long> getService() {
        return studentActivityAssignmentService;
    }
}
