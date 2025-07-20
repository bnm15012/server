package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.BulkUploadEntry;
import com.dancestudio.erp.response.BulkUploadResponse;
import org.springframework.http.ResponseEntity;

public interface BulkUploadService extends BaseService<BulkUploadEntry, BulkUploadResponse, Long> {

    ResponseEntity<BulkUploadResponse> getAllBulkUploads(Long branchId, Integer page, Integer size);

    ResponseEntity<BulkUploadResponse> searchBulkUploads(Long branchId, String entityType, String status);

    ResponseEntity<BulkUploadResponse> processBulkUpload(Long branchId, String entityType, String fileUrl);

}
