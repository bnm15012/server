package com.dancestudio.erp.modules.member.instructor;

import com.dancestudio.erp.base.BaseService;
import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.response.StatusResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Setter(onMethod = @__({ @Autowired }))
@Component
public class InstructorService extends BaseService<InstructorEntry, Long> {

    private InstructorManager instructorManager;

    public ResponseEntity<InstructorResponse> getAllInstructors(Long branchId, MembershipStatus membershipStatus,
            int page, int size, String searchTerm) {
        InstructorResponse response = new InstructorResponse();

        try {
            Page<InstructorEntry> entries = instructorManager.getAllInstructorsByBranch(branchId, membershipStatus,
                    --page, size, searchTerm);
            response.setData(entries.getContent());
            response.setStatus(new StatusResponse(1, "Instructors retrieved successfully", StatusResponse.Type.SUCCESS,
                    Objects.isNull(entries) ? 0 : (int) entries.getTotalElements()));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<InstructorCommunicationResponse> getAllInstructorsForCommunication(Long branchId,
            MembershipStatus membershipStatus, int page, int size) {
        InstructorCommunicationResponse response = new InstructorCommunicationResponse();

        try {
            Page<InstructorCommunicationEntry> entries = instructorManager.getAllInstructorsForCommunication(branchId,
                    membershipStatus, --page, size);
            response.setData(entries.getContent());
            response.setStatus(new StatusResponse(1, "Instructors retrieved successfully", StatusResponse.Type.SUCCESS,
                    Objects.isNull(entries) ? 0 : (int) entries.getTotalElements()));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    protected InstructorEntry doAdd(InstructorEntry entry) throws Exception {
        return instructorManager.add(entry);
    }

    @Override
    protected InstructorEntry doUpdate(Long id, InstructorEntry entry) throws Exception {
        return instructorManager.update(id, entry);
    }

    @Override
    protected void doDelete(Long id) throws Exception {
        instructorManager.delete(id);
    }

    @Override
    protected InstructorEntry doGet(Long id) throws Exception {
        return instructorManager.getById(id);
    }
}
