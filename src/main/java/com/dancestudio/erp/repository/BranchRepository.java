package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    List<Branch> findByStudioId(Long studioId);

    @Query(value = "SELECT whatsapp_status FROM branch WHERE id = :branchId", nativeQuery = true)
    String findWhatsAppStatusByBranchId(@Param("branchId") Long branchId);
    
    @Query("SELECT b FROM Branch b LEFT JOIN FETCH b.studio WHERE b.id = :branchId")
    Optional<Branch> findBranchWithStudioById(@Param("branchId") Long branchId);
}
