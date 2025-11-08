package com.dancestudio.erp.service;

import org.springframework.http.ResponseEntity;

import com.dancestudio.erp.entry.StudentActivityAssignmentEntry;
import com.dancestudio.erp.response.StudentActivityAssignmentResponse;

public interface StudentActivityAssignmentService extends BaseService<StudentActivityAssignmentEntry, StudentActivityAssignmentResponse, Long> {

    ResponseEntity<StudentActivityAssignmentResponse> getAll(Long id, Integer page, Integer size);

}
