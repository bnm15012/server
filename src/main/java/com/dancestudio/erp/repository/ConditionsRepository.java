package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Conditions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConditionsRepository extends JpaRepository<Conditions, Long> {

    @Query("""
        SELECT c FROM Conditions c WHERE c.branch.id = :branchId AND (:entityType IS NULL OR c.entityType = :entityType) AND (:activityType IS NULL OR c.activityType = :activityType)
    """)
    Page<Conditions> findByBranchIdAndFilters(@Param("branchId") Long branchId, @Param("entityType") String entityType, @Param("activityType") String activityType, Pageable pageable);
    
    @Query("""
        SELECT COUNT(c) FROM Conditions c WHERE c.branch.id = :branchId AND (:entityType IS NULL OR c.entityType = :entityType) AND (:activityType IS NULL OR c.activityType = :activityType)
    """)
    long countByBranchIdAndFilters(@Param("branchId") Long branchId, @Param("entityType") String entityType, @Param("activityType") String activityType);
}
