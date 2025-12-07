package com.dancestudio.erp.modules.member.student;

import org.springframework.http.ResponseEntity;

import com.dancestudio.erp.service.BaseService;


public interface StudentActivityAssignmentService extends BaseService<StudentActivityAssignmentEntry, StudentActivityAssignmentResponse, Long> {

    ResponseEntity<StudentActivityAssignmentResponse> getAll(Long id, Integer page, Integer size);

}
