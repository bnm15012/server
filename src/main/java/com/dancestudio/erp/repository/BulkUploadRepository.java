package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.BulkUpload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BulkUploadRepository extends JpaRepository<BulkUpload, Long> {
    
    @Query("SELECT j FROM BulkUpload j WHERE (:branchId IS NULL OR j.branch.id = :branchId) AND " +
           "(:entityType IS NULL OR j.entityType = :entityType) AND (:status IS NULL OR j.status = :status)")
    List<BulkUpload> findByBranchIdAndEntityTypeAndStatus(@Param("branchId") Long branchId, @Param("entityType") String entityType, @Param("status") String status);
    
    @Query("SELECT b FROM BulkUpload b WHERE b.branch.id = :branchId")
    Page<BulkUpload> findByBranchId(@Param("branchId") Long branchId, Pageable pageable);
    
    @Query("SELECT s FROM BulkUpload s WHERE s.branch.id = :branchId")
    List<BulkUpload> findAllByBranchId(@Param("branchId") Long branchId);

    @Query("SELECT COUNT(s) FROM BulkUpload s WHERE s.branch.id = :branchId")
    Long countByBranchId(@Param("branchId") Long branchId);
}
