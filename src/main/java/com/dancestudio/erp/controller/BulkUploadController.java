package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.BulkUploadEntry;
import com.dancestudio.erp.response.BulkUploadResponse;
import com.dancestudio.erp.service.BaseService;
import com.dancestudio.erp.service.BulkUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobs/bulk-uploads")
@RequiredArgsConstructor
public class BulkUploadController extends BaseController<BulkUploadEntry, BulkUploadResponse, Long> {

    private final BulkUploadService bulkUploadService;

    @Override
    protected BaseService<BulkUploadEntry, BulkUploadResponse, Long> getService() {
        return bulkUploadService;
    }

    @PostMapping("/process")
    public ResponseEntity<BulkUploadResponse> processBulkUpload(@RequestBody BulkUploadEntry entry) {
        return bulkUploadService.processBulkUpload(entry);
    }

    @GetMapping("/getAll/{branchId}")
    public ResponseEntity<BulkUploadResponse> getAllBulkUploadJobs(@PathVariable Long branchId, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return bulkUploadService.getAllBulkUploads(branchId, page, size);
    }

    @GetMapping("/jobs/search")
    public ResponseEntity<BulkUploadResponse> searchBulkUploadJobs(@RequestParam(required = false) Long branchId, @RequestParam(required = false) String entityType, @RequestParam(required = false) String status) {
        return bulkUploadService.searchBulkUploads(branchId, entityType, status);
    }


}
