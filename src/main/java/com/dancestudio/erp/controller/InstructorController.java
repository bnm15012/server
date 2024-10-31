package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.InstructorEntry;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.response.InstructorResponse;
import com.dancestudio.erp.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instructors")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @PostMapping("/add")
    public InstructorResponse addInstructor(@RequestBody InstructorEntry instructorEntry) {
        return instructorService.addInstructor(instructorEntry);
    }

    @PutMapping("/update/{instructorId}")
    public InstructorResponse updateInstructor(@PathVariable Long instructorId, @RequestBody InstructorEntry instructorEntry) {
        return instructorService.updateInstructor(instructorId, instructorEntry);
    }

    @DeleteMapping("/delete/{instructorId}")
    public void deleteInstructor(@PathVariable Long instructorId) {
        instructorService.deleteInstructor(instructorId);
    }

    @GetMapping("/get/{instructorId}")
    public InstructorResponse getInstructorById(@PathVariable Long instructorId) {
        return instructorService.getInstructorById(instructorId);
    }

    @GetMapping("/getAllInstructors/{studioId}")
    public InstructorResponse getAllInstructors(@PathVariable Long studioId,
                                                @RequestParam(required = false) MembershipStatus membershipStatus) {
        return instructorService.getAllInstructors(studioId, membershipStatus);
    }
}
