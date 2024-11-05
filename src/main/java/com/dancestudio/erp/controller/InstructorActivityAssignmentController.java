package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.InstructorActivityAssignmentEntry;
import com.dancestudio.erp.response.InstructorActivityAssignmentResponse;
import com.dancestudio.erp.service.InstructorActivityAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instructorActivities")
public class InstructorActivityAssignmentController {

    @Autowired
    private InstructorActivityAssignmentService instructorActivityAssignmentService;

    @PostMapping("/add")
    public ResponseEntity<InstructorActivityAssignmentResponse> addInstructorActivityAssignment(@RequestBody InstructorActivityAssignmentEntry instructorActivityAssignmentEntry) {
        return instructorActivityAssignmentService.addInstructorActivityAssignment(instructorActivityAssignmentEntry);
    }

    @PutMapping("/update/{instructorActivityAssignmentId}")
    public ResponseEntity<InstructorActivityAssignmentResponse> updateInstructorActivityAssignment(@PathVariable Long instructorActivityAssignmentId, @RequestBody InstructorActivityAssignmentEntry instructorActivityAssignmentEntry) {
        return instructorActivityAssignmentService.updateInstructorActivityAssignment(instructorActivityAssignmentId, instructorActivityAssignmentEntry);
    }

    @DeleteMapping("/delete/{instructorActivityAssignmentId}")
    public ResponseEntity<Void> deleteInstructorActivityAssignment(@PathVariable Long instructorActivityAssignmentId) {
        return instructorActivityAssignmentService.deleteInstructorActivityAssignment(instructorActivityAssignmentId);
    }

    @GetMapping("/get/{instructorActivityAssignmentId}")
    public ResponseEntity<InstructorActivityAssignmentResponse> getInstructorActivityAssignmentById(@PathVariable Long instructorActivityAssignmentId) {
        return instructorActivityAssignmentService.getInstructorActivityAssignmentById(instructorActivityAssignmentId);
    }
}
