package com.dancestudio.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Entity
@Table(name = "bulk_upload")
@Data
@EqualsAndHashCode(callSuper = true)
public class BulkUpload extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @Column(nullable = false)
    private String entityType;

    @Column(name = "status", nullable = false)
    @ColumnDefault("'PENDING'")
    private String status;

    private int totalRecords;
    private int processedRecords;
    private int successfulRecords;
    private int failedRecords;

    @Column(columnDefinition = "TEXT")
    private String errorMessage;

    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "completed_at")
    private Date completedAt;

}
