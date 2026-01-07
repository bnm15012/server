package com.dancestudio.erp.modules.member.instructor.instructorActivityAssignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dancestudio.erp.base.BaseController;
import com.dancestudio.erp.base.BaseService;

@RestController
@RequestMapping("/instructorActivities")
public class InstructorActivityAssignmentController
        extends BaseController<InstructorActivityAssignmentEntry, Long> {

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
    protected BaseService<InstructorActivityAssignmentEntry, Long> getService() {
        return instructorActivityAssignmentService;
    }
}
