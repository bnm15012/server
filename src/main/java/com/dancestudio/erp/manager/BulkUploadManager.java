package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.BulkUploadEntry;

import java.util.List;

public interface BulkUploadManager extends BaseManager<BulkUploadEntry, Long> {

    BulkUploadEntry processBulkUpload(BulkUploadEntry jobEntry, Long branchId, String entityType, String fileUrl);

    BulkUploadEntry updateBulkUploadStatus(Long id, String status, String errorMessage);

    List<BulkUploadEntry> getAllJobs(Long branchId, Integer page, Integer size);

    Long countJobsByBranchId(Long branchId);

}
