package com.dancestudio.erp.service.impl;


import com.dancestudio.erp.entry.BranchEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.BranchManager;
import com.dancestudio.erp.response.BranchResponse;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.service.BranchService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Setter(onMethod = @__({@Autowired}))
@Component
public class BranchServiceImpl implements BranchService {

    private BranchManager branchManager;

    @Override
    public ResponseEntity<BranchResponse> add(BranchEntry branchEntry) {
        BranchResponse response = new BranchResponse();

        try {
            BranchEntry entry = branchManager.add(branchEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Branch added successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<BranchResponse> update(Long branchId, BranchEntry branchEntry) {
        BranchResponse response = new BranchResponse();

        try {
            BranchEntry entry = branchManager.update(branchId, branchEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Branch updated successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EntityNotFoundException e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<Void> delete(Long branchId) {
        try {
            branchManager.delete(branchId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<BranchResponse> get(Long branchId) {
        BranchResponse response = new BranchResponse();

        try {
            BranchEntry entry = branchManager.getById(branchId);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Branch retrieved successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EntityNotFoundException e) {
            response.setData(Collections.emptyList());
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<BranchResponse> getAllBranchesOfStudio(Long studioId) {
        BranchResponse response = new BranchResponse();

        try {
            List<BranchEntry> entries = branchManager.findByStudioId(studioId);
            response.setData(entries);
            response.setStatus(new StatusResponse(1, "Branches retrieved successfully", StatusResponse.Type.SUCCESS, entries.size()));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EntityNotFoundException e) {
            response.setData(Collections.emptyList());
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<BranchResponse> enableDisableBranch(Long branchId, boolean flag) {
        BranchResponse response = new BranchResponse();

        try {
            BranchEntry entry = branchManager.enableDisableBranch(branchId, flag);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Branch enabled / disabled successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EntityNotFoundException e) {
            response.setData(Collections.emptyList());
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
