package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.InstructorEntry;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.response.InstructorResponse;
import com.dancestudio.erp.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/instructors")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @PostMapping("/add")
    public ResponseEntity<InstructorResponse> addInstructor(@RequestBody InstructorEntry instructorEntry) {
        return instructorService.addInstructor(instructorEntry);
    }

    @PutMapping("/update/{instructorId}")
    public ResponseEntity<InstructorResponse> updateInstructor(@PathVariable Long instructorId, @RequestBody InstructorEntry instructorEntry) {
        return instructorService.updateInstructor(instructorId, instructorEntry);
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<InstructorResponse> uploadImage(@RequestParam("image") MultipartFile file) {
        return instructorService.uploadImage(file);
    }

    @DeleteMapping("/delete/{instructorId}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable Long instructorId) {
        return instructorService.deleteInstructor(instructorId);
    }

    @GetMapping("/get/{instructorId}")
    public ResponseEntity<InstructorResponse> getInstructorById(@PathVariable Long instructorId) {
        return instructorService.getInstructorById(instructorId);
    }

    @GetMapping("/getAllInstructors/{studioId}")
    public ResponseEntity<InstructorResponse> getAllInstructors(@PathVariable Long studioId,
                                                @RequestParam(required = false) MembershipStatus membershipStatus) {
        return instructorService.getAllInstructors(studioId, membershipStatus);
    }
}
