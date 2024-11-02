package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.StudentEntry;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.response.StudentResponse;
import com.dancestudio.erp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/add")
    public ResponseEntity<StudentResponse> addStudent(@RequestBody StudentEntry studentEntry) {
        return studentService.addStudent(studentEntry);
    }

    @PutMapping("/update/{studentId}")
    public ResponseEntity<StudentResponse> updateStudent(@PathVariable Long studentId, @RequestBody StudentEntry studentEntry) {
        return studentService.updateStudent(studentId, studentEntry);
    }

    @DeleteMapping("/delete/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) {
        return studentService.deleteStudent(studentId);
    }

    @GetMapping("/get/{studentId}")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable Long studentId) {
        return studentService.getStudentById(studentId);
    }

    @GetMapping("/getAllStudents/{studioId}")
    public ResponseEntity<StudentResponse> getAllStudents(@PathVariable Long studioId,
                                          @RequestParam(required = false) Long activityId,
                                          @RequestParam(required = false) MembershipStatus membershipStatus) {
        return studentService.getAllStudents(studioId, activityId, membershipStatus);
    }

    @PostMapping("/sendSubscriptionRenewalReminder/{studentId}")
    public ResponseEntity<StudentResponse> sendSubscriptionRenewalReminder(@PathVariable Long studentId) {
        return studentService.sendSubscriptionRenewalReminder(studentId);
    }
}
