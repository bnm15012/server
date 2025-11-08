package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.StudentActivityAssignmentEntry;
import com.dancestudio.erp.response.StudentActivityAssignmentResponse;
import com.dancestudio.erp.service.StudentActivityAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/studentActivities")
public class StudentActivityAssignmentController extends BaseController<StudentActivityAssignmentEntry, StudentActivityAssignmentResponse, Long> {

    @Autowired
    private StudentActivityAssignmentService studentActivityAssignmentService;

    @Override
    public ResponseEntity<StudentActivityAssignmentResponse> add(@RequestBody StudentActivityAssignmentEntry studentActivityAssignmentEntry) {
        return studentActivityAssignmentService.add(studentActivityAssignmentEntry);
    }

    @Override
    public ResponseEntity<StudentActivityAssignmentResponse> update(@PathVariable Long id, @RequestBody StudentActivityAssignmentEntry studentActivityAssignmentEntry) {
        return studentActivityAssignmentService.update(id, studentActivityAssignmentEntry);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return studentActivityAssignmentService.delete(id);
    }

    @Override
    public ResponseEntity<StudentActivityAssignmentResponse> get(@PathVariable Long id) {
        return studentActivityAssignmentService.get(id);
    }


    @GetMapping("/getAll/{studentId}")
    public ResponseEntity<StudentActivityAssignmentResponse> getAllInstructors(
        @PathVariable Long studentId,
        @RequestParam(defaultValue = "1") Integer page, 
        @RequestParam(defaultValue = "10") Integer size
    ) {
        return studentActivityAssignmentService.getAll(studentId, page, size);
    }
}
