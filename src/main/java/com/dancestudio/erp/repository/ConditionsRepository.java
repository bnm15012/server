package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Conditions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConditionsRepository extends JpaRepository<Conditions, Long> {
    Optional<Conditions> findByEntityTypeAndBranchId(String entityType, Long branchId);
    
    @Query("SELECT c FROM Conditions c WHERE c.branch.id = :branchId")
    Page<Conditions> findByBranchId(@Param("branchId") Long branchId, Pageable pageable);
    
    @Query("SELECT COUNT(c) FROM Conditions c WHERE c.branch.id = :branchId")
    long countByBranchId(@Param("branchId") Long branchId);
}
