package com.dancestudio.erp.entry;

import com.dancestudio.erp.util.DateUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Data
public class BulkUploadEntry {

    private Long id;
    private BranchEntry branchEntry;
    private String entityType;
    private String status;
    private Integer totalRecords;
    private Integer processedRecords;
    private Integer successfulRecords;
    private Integer failedRecords;

    private String fileUrl;
    private String fileName;
    private Date completedAt;

    private List<String> errorMessages = new ArrayList<>();

    public void incrementSuccessful() {
        this.successfulRecords++;
        incrementProcessed();
    }

    public void incrementFailed() {
        this.failedRecords++;
        incrementProcessed();
    }

    public void incrementProcessed() {
        this.processedRecords++;
    }


    public void markAsCompleted() {
        this.status = "COMPLETED";
        this.completedAt = DateUtil.getCurrentDateUTC();
    }

    public void markAsFailed(String error) {
        this.status = "FAILED";
        this.errorMessages = Collections.singletonList(error);
        this.completedAt = DateUtil.getCurrentDateUTC();
    }

    public boolean isCompleted() {
        return "COMPLETED".equals(status) || "FAILED".equals(status);
    }

}
