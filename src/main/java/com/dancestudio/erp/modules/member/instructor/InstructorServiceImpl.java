package com.dancestudio.erp.modules.member.instructor;


import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.response.StatusResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Setter(onMethod = @__({@Autowired}))
@Component
public class InstructorServiceImpl implements InstructorService {

    private InstructorManager instructorManager;

    @Override
    public ResponseEntity<InstructorResponse> add(InstructorEntry instructorEntry) {
        InstructorResponse response = new InstructorResponse();

        try {
            InstructorEntry entry = instructorManager.add(instructorEntry);
            if (entry != null) {
                response.setData(Collections.singletonList(entry));
                response.setStatus(new StatusResponse(1, "Instructor added successfully", StatusResponse.Type.SUCCESS));
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                response.setStatus(new StatusResponse(0, "Failed to add instructor", StatusResponse.Type.ERROR));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<InstructorResponse> update(Long instructorId, InstructorEntry instructorEntry) {
        InstructorResponse response = new InstructorResponse();

        try {
            InstructorEntry entry = instructorManager.update(instructorId, instructorEntry);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Instructor updated successfully", StatusResponse.Type.SUCCESS));
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException ex) {
            response.setStatus(new StatusResponse(1, "Instructor not found", StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(1, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<Void> delete(Long instructorId) {
        try {
            instructorManager.delete(instructorId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<InstructorResponse> get(Long instructorId) {
        InstructorResponse response = new InstructorResponse();

        try {
            InstructorEntry entry = instructorManager.getById(instructorId);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Instructor found", StatusResponse.Type.SUCCESS));
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException ex) {
            response.setStatus(new StatusResponse(1, "Instructor not found", StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<InstructorResponse> getAllInstructors(Long branchId, MembershipStatus membershipStatus, int page, int size, String searchTerm) {
        InstructorResponse response = new InstructorResponse();

        try {
            List<InstructorEntry> entries = instructorManager.getAllInstructorsByBranch(branchId, membershipStatus, --page, size, searchTerm);
            long totalSize = instructorManager.getCountInstructorByBranchId(branchId);
            response.setData(entries);
            response.setStatus(new StatusResponse(1, "Instructors retrieved successfully", StatusResponse.Type.SUCCESS, Objects.isNull(entries) ? 0 : (int) totalSize));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<InstructorCommunicationResponse> getAllInstructorsForCommunication(Long branchId, MembershipStatus membershipStatus, int page, int size) {
        InstructorCommunicationResponse response = new InstructorCommunicationResponse();

        try {
            List<InstructorCommunicationEntry> entries = instructorManager.getAllInstructorsForCommunication(branchId, membershipStatus, --page, size);
            long totalSize = instructorManager.getCountInstructorByBranchId(branchId);
            response.setData(entries);
            response.setStatus(new StatusResponse(1, "Instructors retrieved successfully", StatusResponse.Type.SUCCESS, Objects.isNull(entries) ? 0 : (int) totalSize));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
