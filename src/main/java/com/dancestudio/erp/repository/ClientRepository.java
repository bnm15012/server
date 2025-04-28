package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT s FROM Client s WHERE s.branch.id = :branchId AND (:startMonth = 0 OR month(s.createdOn) >= :startMonth) AND (:endMonth = 0 OR month(s.createdOn) <= :endMonth) AND (:searchTerm IS NULL OR LOWER(s.groupName) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Client> findAllByBranchId(@Param("branchId") Long branchId, @Param("startMonth") Long startMonth, @Param("endMonth") Long endMonth, Pageable pageable, @Param("searchTerm") String searchTerm);

    @Query("SELECT s FROM Client s WHERE s.branch.id = :branchId AND (:searchTerm IS NULL OR LOWER(s.groupName) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Client> findClientsByBranchId(@Param("branchId") Long branchId, Pageable pageable, @Param("searchTerm") String searchTerm);

    @Query("SELECT COUNT(s) FROM Client s WHERE s.branch.id = :branchId")
    Long countClientsByBranchId(@Param("branchId") Long branchId);

    @Query("SELECT COUNT(s) FROM Client s WHERE s.branch.id = :branchId and month(s.createdOn) >= :startMonth and month(s.createdOn) <= :endMonth")
    Long countClientsByBranchIdAndMonthLong(@Param("branchId") Long branchId, @Param("startMonth") Long startMonth, @Param("endMonth") Long endMonth);

    List<Client> findByGroupNameContainingIgnoreCase(String groupName);

}