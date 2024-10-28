package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.StudentEntry;
import com.dancestudio.erp.response.StudentResponse;
import com.dancestudio.erp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public StudentResponse addStudent(@RequestBody StudentEntry studentEntry) {
        return studentService.addStudent(studentEntry);
    }

    @PutMapping("/{studentId}")
    public StudentResponse updateStudent(@PathVariable Long studentId, @RequestBody StudentEntry studentEntry) {
        return studentService.updateStudent(studentId, studentEntry);
    }

    @DeleteMapping("/{studentId}")
    public void deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
    }

    @GetMapping("/{studentId}")
    public StudentResponse getStudentById(@PathVariable Long studentId) {
        return studentService.getStudentById(studentId);
    }

    @GetMapping
    public StudentResponse getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping("/{studentId}/resendEmail")
    public void resendEmail(@PathVariable Long studentId) {
        studentService.resendEmail(studentId);
    }
}
