package com.dancestudio.erp.service;

import org.springframework.http.ResponseEntity;

import com.dancestudio.erp.entry.InstructorActivityAssignmentEntry;
import com.dancestudio.erp.response.InstructorActivityAssignmentResponse;

public interface InstructorActivityAssignmentService extends BaseService<InstructorActivityAssignmentEntry, InstructorActivityAssignmentResponse, Long> {

    ResponseEntity<InstructorActivityAssignmentResponse> getAll(Long id, Integer page, Integer size);

}
