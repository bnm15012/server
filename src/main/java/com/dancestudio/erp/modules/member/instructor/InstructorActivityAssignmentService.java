package com.dancestudio.erp.modules.member.instructor;


import org.springframework.http.ResponseEntity;
import com.dancestudio.erp.service.BaseService;

public interface InstructorActivityAssignmentService extends BaseService<InstructorActivityAssignmentEntry, InstructorActivityAssignmentResponse, Long> {

    ResponseEntity<InstructorActivityAssignmentResponse> getAll(Long id, Integer page, Integer size);

}
