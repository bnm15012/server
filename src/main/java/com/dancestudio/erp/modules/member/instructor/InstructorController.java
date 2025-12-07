package com.dancestudio.erp.modules.member.instructor;

import com.dancestudio.erp.controller.BaseController;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instructors")
public class InstructorController extends BaseController<InstructorEntry, InstructorResponse, Long> {

    @Autowired
    private InstructorService instructorService;

    @GetMapping("/getAll/{branchId}")
    public ResponseEntity<InstructorResponse> getAllInstructors(@PathVariable Long branchId,
            @RequestParam(required = false) MembershipStatus membershipStatus,
            @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "-1") int size,
            @RequestParam(required = false) String searchTerm) {
        return instructorService.getAllInstructors(branchId, membershipStatus, page, size, searchTerm);
    }

    @GetMapping("/getAllInstructorsForCommunication/{branchId}")
    public ResponseEntity<InstructorCommunicationResponse> getAllInstructors(
            @PathVariable Long branchId,
            @RequestParam(required = false) MembershipStatus membershipStatus,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "-1") int size) {
        return instructorService.getAllInstructorsForCommunication(branchId, membershipStatus, page, size);
    }

    @Override
    protected BaseService<InstructorEntry, InstructorResponse, Long> getService() {
        return instructorService;
    }

}
