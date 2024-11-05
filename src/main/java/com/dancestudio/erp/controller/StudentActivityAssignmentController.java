package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.StudentActivityAssignmentEntry;
import com.dancestudio.erp.response.StudentActivityAssignmentResponse;
import com.dancestudio.erp.service.StudentActivityAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/studentActivities")
public class StudentActivityAssignmentController {

    @Autowired
    private StudentActivityAssignmentService studentActivityAssignmentService;

    @PostMapping("/add")
    public ResponseEntity<StudentActivityAssignmentResponse> addStudentActivityAssignment(@RequestBody StudentActivityAssignmentEntry studentActivityAssignmentEntry) {
        return studentActivityAssignmentService.addStudentActivityAssignment(studentActivityAssignmentEntry);
    }

    @PutMapping("/update/{studentActivityAssignmentId}")
    public ResponseEntity<StudentActivityAssignmentResponse> updateStudentActivityAssignment(@PathVariable Long studentActivityAssignmentId, @RequestBody StudentActivityAssignmentEntry studentActivityAssignmentEntry) {
        return studentActivityAssignmentService.updateStudentActivityAssignment(studentActivityAssignmentId, studentActivityAssignmentEntry);
    }

    @DeleteMapping("/delete/{studentActivityAssignmentId}")
    public ResponseEntity<Void> deleteStudentActivityAssignment(@PathVariable Long studentActivityAssignmentId) {
        return studentActivityAssignmentService.deleteStudentActivityAssignment(studentActivityAssignmentId);
    }

    @GetMapping("/get/{studentActivityAssignmentId}")
    public ResponseEntity<StudentActivityAssignmentResponse> getStudentActivityAssignmentById(@PathVariable Long studentActivityAssignmentId) {
        return studentActivityAssignmentService.getStudentActivityAssignmentById(studentActivityAssignmentId);
    }
}
