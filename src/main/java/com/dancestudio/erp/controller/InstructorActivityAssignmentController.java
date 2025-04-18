package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.InstructorActivityAssignmentEntry;
import com.dancestudio.erp.response.InstructorActivityAssignmentResponse;
import com.dancestudio.erp.service.InstructorActivityAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instructorActivities")
public class InstructorActivityAssignmentController extends BaseController<InstructorActivityAssignmentEntry, InstructorActivityAssignmentResponse, Long> {

    @Autowired
    private InstructorActivityAssignmentService instructorActivityAssignmentService;

    @Override
    public ResponseEntity<InstructorActivityAssignmentResponse> add(@RequestBody InstructorActivityAssignmentEntry instructorActivityAssignmentEntry) {
        return instructorActivityAssignmentService.add(instructorActivityAssignmentEntry);
    }

    @Override
    public ResponseEntity<InstructorActivityAssignmentResponse> update(@PathVariable Long id, @RequestBody InstructorActivityAssignmentEntry instructorActivityAssignmentEntry) {
        return instructorActivityAssignmentService.update(id, instructorActivityAssignmentEntry);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return instructorActivityAssignmentService.delete(id);
    }

    @Override
    public ResponseEntity<InstructorActivityAssignmentResponse> get(@PathVariable Long id) {
        return instructorActivityAssignmentService.get(id);
    }
}
