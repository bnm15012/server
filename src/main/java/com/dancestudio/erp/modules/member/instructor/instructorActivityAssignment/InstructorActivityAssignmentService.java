package com.dancestudio.erp.modules.member.instructor.instructorActivityAssignment;

import com.dancestudio.erp.base.BaseService;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.response.StatusResponse;
import lombok.Setter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Setter(onMethod = @__({ @Autowired }))
@Component
public class InstructorActivityAssignmentService extends BaseService<InstructorActivityAssignmentEntry, Long> {

    private InstructorActivityAssignmentManager instructorActivityAssignmentManager;

    public ResponseEntity<InstructorActivityAssignmentResponse> getAll(Long id, Integer page, Integer size) {

        InstructorActivityAssignmentResponse response = new InstructorActivityAssignmentResponse();
        try {
            Page<InstructorActivityAssignment> entries = instructorActivityAssignmentManager
                    .getAssignmentsByInstructor(id, --page, size);
            response.setData(entries.getContent().stream().map(a -> {
                try {
                    return InstructorActivityAssignmentConvertor.convertToEntry(a);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList()));
            response.setStatus(new StatusResponse(1, "Instructors retrieved successfully", StatusResponse.Type.SUCCESS,
                    Objects.isNull(entries) ? 0 : entries.getTotalElements()));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    protected InstructorActivityAssignmentEntry doAdd(InstructorActivityAssignmentEntry entry) throws Exception {
        return instructorActivityAssignmentManager.add(entry);
    }

    @Override
    protected InstructorActivityAssignmentEntry doUpdate(Long id, InstructorActivityAssignmentEntry entry) throws BeansException, EntityNotFoundException, Exception {
        return instructorActivityAssignmentManager.update(id, entry);
    }

    @Override
    protected void doDelete(Long id) throws Exception {
        instructorActivityAssignmentManager.delete(id);
    }

    @Override
    protected InstructorActivityAssignmentEntry doGet(Long id) throws Exception {
        return instructorActivityAssignmentManager.getById(id);
    }
}
