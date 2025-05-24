package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    List<Branch> findByStudioId(Long studioId);

    @Query(value = "SELECT whatsapp_status FROM branch WHERE id = :branchId", nativeQuery = true)
    String findWhatsAppStatusByBranchId(@Param("branchId") Long branchId);
}
