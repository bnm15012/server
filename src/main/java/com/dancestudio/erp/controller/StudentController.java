package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.StudentEntry;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.response.StudentResponse;
import com.dancestudio.erp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/add")
    public StudentResponse addStudent(@RequestBody StudentEntry studentEntry) {
        return studentService.addStudent(studentEntry);
    }

    @PutMapping("/update/{studentId}")
    public StudentResponse updateStudent(@PathVariable Long studentId, @RequestBody StudentEntry studentEntry) {
        return studentService.updateStudent(studentId, studentEntry);
    }

    @DeleteMapping("/delete/{studentId}")
    public void deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
    }

    @GetMapping("/get/{studentId}")
    public StudentResponse getStudentById(@PathVariable Long studentId) {
        return studentService.getStudentById(studentId);
    }

    @GetMapping("/getAllStudents/{studioId}")
    public StudentResponse getAllStudents(@PathVariable Long studioId,
                                          @RequestParam(required = false) Long activityId,
                                          @RequestParam(required = false) MembershipStatus membershipStatus) {
        return studentService.getAllStudents(studioId, activityId, membershipStatus);
    }

    @PostMapping("/{studentId}/resendEmail")
    public void resendEmail(@PathVariable Long studentId) {
        studentService.resendEmail(studentId);
    }
}
