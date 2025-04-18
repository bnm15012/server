package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.InstructorEntry;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.response.InstructorResponse;
import com.dancestudio.erp.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instructors")
public class InstructorController extends BaseController<InstructorEntry, InstructorResponse, Long> {

    @Autowired
    private InstructorService instructorService;

    @Override
    public ResponseEntity<InstructorResponse> add(@RequestBody InstructorEntry instructorEntry) {
        return instructorService.add(instructorEntry);
    }

    @Override
    public ResponseEntity<InstructorResponse> update(@PathVariable Long id, @RequestBody InstructorEntry instructorEntry) {
        return instructorService.update(id, instructorEntry);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return instructorService.delete(id);
    }

    @Override
    public ResponseEntity<InstructorResponse> get(@PathVariable Long id) {
        return instructorService.get(id);
    }

    @GetMapping("/getAllInstructors/{studioId}")
    public ResponseEntity<InstructorResponse> getAllInstructors(@PathVariable Long studioId,
                                                @RequestParam(required = false) MembershipStatus membershipStatus,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "-1") int size) {
        return instructorService.getAllInstructors(studioId, membershipStatus, page, size);
    }
}
