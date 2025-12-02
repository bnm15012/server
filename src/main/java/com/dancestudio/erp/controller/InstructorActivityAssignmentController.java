package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.InstructorActivityAssignmentEntry;
import com.dancestudio.erp.response.InstructorActivityAssignmentResponse;
import com.dancestudio.erp.service.BaseService;
import com.dancestudio.erp.service.InstructorActivityAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instructorActivities")
public class InstructorActivityAssignmentController
        extends BaseController<InstructorActivityAssignmentEntry, InstructorActivityAssignmentResponse, Long> {

    @Autowired
    private InstructorActivityAssignmentService instructorActivityAssignmentService;

    @GetMapping("/getAll/{instructorId}")
    public ResponseEntity<InstructorActivityAssignmentResponse> getAllInstructors(
            @PathVariable Long instructorId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return instructorActivityAssignmentService.getAll(instructorId, page, size);
    }

    @Override
    protected BaseService<InstructorActivityAssignmentEntry, InstructorActivityAssignmentResponse, Long> getService() {
        return instructorActivityAssignmentService;
    }
}
