package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.entry.BranchEntry;
import com.dancestudio.erp.entry.BulkUploadEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.BulkUploadManager;
import com.dancestudio.erp.response.BulkUploadResponse;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.service.BulkUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BulkUploadServiceImpl implements BulkUploadService {

    private final BulkUploadManager bulkUploadManager;

    @Override
    public ResponseEntity<BulkUploadResponse> add(BulkUploadEntry jobEntry) {
        BulkUploadResponse response = new BulkUploadResponse();

        try {
            BulkUploadEntry entry = bulkUploadManager.add(jobEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Job added successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<BulkUploadResponse> update(Long jobId, BulkUploadEntry bulkUploadEntry) {
        BulkUploadResponse response = new BulkUploadResponse();

        try {
            BulkUploadEntry entry = bulkUploadManager.update(jobId, bulkUploadEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Job updated successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (com.dancestudio.erp.exception.EntityNotFoundException e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<Void> delete(Long jobId) {
        try {
            bulkUploadManager.delete(jobId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<BulkUploadResponse> get(Long id) {
        BulkUploadResponse response = new BulkUploadResponse();

        try {
            BulkUploadEntry entry = bulkUploadManager.getById(id);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Job fetched successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (com.dancestudio.erp.exception.EntityNotFoundException e) {
            response.setStatus(new StatusResponse(0, "Job not found", StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<BulkUploadResponse> processBulkUpload(BulkUploadEntry entry) {
        BulkUploadResponse response = new BulkUploadResponse();
        BulkUploadEntry jobEntry = new BulkUploadEntry();

        try {
            BulkUploadEntry bulkUploadEntry = new BulkUploadEntry();

            BranchEntry branchEntry = new BranchEntry();
            branchEntry.setBranchId(entry.getBranchEntry().getBranchId());
            bulkUploadEntry.setBranchEntry(branchEntry);
            bulkUploadEntry.setEntityType(entry.getEntityType());
            bulkUploadEntry.setFileUrl(entry.getFileUrl());
            bulkUploadEntry.setFileName(entry.getFileUrl().substring(entry.getFileUrl().lastIndexOf('/') + 1));

            jobEntry = bulkUploadManager.add(bulkUploadEntry);
            jobEntry = bulkUploadManager.processBulkUpload(jobEntry, entry.getBranchEntry().getBranchId(), entry.getEntityType(), entry.getFileUrl());

            jobEntry.setStatus("COMPLETED");
            bulkUploadManager.update(jobEntry.getId(), jobEntry);

            response.setData(Collections.singletonList(jobEntry));
            response.setStatus(new StatusResponse(1, "Bulk upload processed successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            if (jobEntry != null) {
                bulkUploadManager.updateBulkUploadStatus(jobEntry.getId(), "FAILED", e.getMessage());
            }
            response.setStatus(new StatusResponse(0, "Error processing bulk upload: " + e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<BulkUploadResponse> getAllBulkUploads(Long branchId, Integer page, Integer size) {

        BulkUploadResponse response = new BulkUploadResponse();
        try {
            List<BulkUploadEntry> entries = bulkUploadManager.getAllJobs(branchId, page, size);
            long clientCount = bulkUploadManager.countJobsByBranchId(branchId);
            response.setData(entries);
            response.setStatus(new StatusResponse(1, "Clients retrieved successfully", StatusResponse.Type.SUCCESS, (int) clientCount));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<BulkUploadResponse> searchBulkUploads(Long branchId, String entityType, String status) {
        return null;
    }

}
