package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.entry.StudentEntry;
import com.dancestudio.erp.manager.StudentManager;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.response.StudentResponse;
import com.dancestudio.erp.service.StudentService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Setter(onMethod = @__({@Autowired}))
@Component
public class StudentServiceImpl implements StudentService {

    private StudentManager studentManager;

    @Override
    public StudentResponse addStudent(StudentEntry studentEntry) {
        StudentResponse response = new StudentResponse();

        StudentEntry entry = studentManager.addStudent(studentEntry);
        response.setData(Collections.singletonList(entry));
        response.setStatus(new StatusResponse(1, StatusResponse.Type.SUCCESS, Objects.isNull(entry) ? 0 : 1));

        return response;
    }

    @Override
    public StudentResponse updateStudent(Long studentId, StudentEntry studentEntry) {
        StudentResponse response = new StudentResponse();

        StudentEntry entry = studentManager.updateStudent(studentId, studentEntry);
        response.setData(Collections.singletonList(entry));
        response.setStatus(new StatusResponse(1, StatusResponse.Type.SUCCESS, Objects.isNull(entry) ? 0 : 1));

        return response;
    }

    @Override
    public void deleteStudent(Long studentId) {
        studentManager.deleteStudent(studentId);
    }

    @Override
    public StudentResponse getStudentById(Long studentId) {
        StudentResponse response = new StudentResponse();

        StudentEntry entry = studentManager.getStudentById(studentId);
        response.setData(Collections.singletonList(entry));
        response.setStatus(new StatusResponse(1, StatusResponse.Type.SUCCESS, Objects.isNull(entry) ? 0 : 1));

        return response;
    }

    @Override
    public StudentResponse getAllStudents(Long studioId, Long activityId) {
        StudentResponse response = new StudentResponse();

        List<StudentEntry> entry = studentManager.getAllStudentsByStudio(studioId, activityId);
        response.setData(entry);
        response.setStatus(new StatusResponse(1, StatusResponse.Type.SUCCESS, Objects.isNull(entry) ? 0 : 1));

        return response;
    }

    @Override
    public void resendEmail(Long studentId) {
        studentManager.resendEmail(studentId);
    }

}
