package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.BulkUpload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BulkUploadRepository extends JpaRepository<BulkUpload, Long> {

    List<BulkUpload> findByBranchIdAndEntityTypeAndStatus(Long branchId, String entityType, String status);

    Page<BulkUpload> findByBranchId(Long branchId, Pageable pageable);

    Long countByBranchId(Long branchId);
}
