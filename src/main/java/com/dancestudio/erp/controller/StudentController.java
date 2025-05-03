package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.StudentEntry;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.response.StudentCommunicationResponse;
import com.dancestudio.erp.response.StudentResponse;
import com.dancestudio.erp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController extends BaseController<StudentEntry, StudentResponse, Long> {

    @Autowired private StudentService studentService;

    @Override
    public ResponseEntity<StudentResponse> add(@RequestBody StudentEntry studentEntry) {
        return studentService.add(studentEntry);
    }

    @Override
    public ResponseEntity<StudentResponse> update(@PathVariable Long id, @RequestBody StudentEntry studentEntry) {
        return studentService.update(id, studentEntry);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return studentService.delete(id);
    }

    @Override
    public ResponseEntity<StudentResponse> get(@PathVariable Long id) {
        return studentService.get(id);
    }

    @GetMapping("/getAllStudents/{studioId}")
    public ResponseEntity<StudentResponse> getAllStudents(
            @PathVariable Long studioId,
            @RequestParam(required = false) Long activityId,
            @RequestParam(required = false) MembershipStatus membershipStatus,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "-1") int size,
            @RequestParam(required = false) String searchTerm) {
        return studentService.getAllStudents(studioId, activityId, membershipStatus, page, size, searchTerm);
    }

    @GetMapping("/getAllStudentsForCommunication/{studioId}")
    public ResponseEntity<StudentCommunicationResponse> getAllStudents(
            @PathVariable Long branchId,
            @RequestParam(required = false) MembershipStatus membershipStatus,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "-1") int size) {
        return studentService.getAllStudentsForCommunication(branchId, membershipStatus, page, size);
    }

    @PostMapping("/sendSubscriptionRenewalReminder/{studentId}/{activityId}")
    public ResponseEntity<StudentResponse> sendSubscriptionRenewalReminder(@PathVariable Long studentId, @PathVariable Long activityId) {
        return studentService.sendSubscriptionRenewalReminder(studentId, activityId);
    }
}
