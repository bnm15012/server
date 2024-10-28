package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.StudentEntry;
import com.dancestudio.erp.response.StudentResponse;

public interface StudentService {

    StudentResponse addStudent(StudentEntry studentEntry);

    StudentResponse updateStudent(Long studentId, StudentEntry studentEntry);

    void deleteStudent(Long studentId);

    StudentResponse getStudentById(Long studentId);

    StudentResponse getAllStudents();

    void resendEmail(Long studentId);
}
