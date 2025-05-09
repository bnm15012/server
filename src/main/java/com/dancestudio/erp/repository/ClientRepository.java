package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface ClientRepository extends JpaRepository<Client, Long> {

    // ✅ Paginated or full data with date range and searchTerm support
    @Query("""
        SELECT c 
          FROM Client c 
         WHERE c.branch.id = :branchId 
           AND (:startDate IS NULL OR c.createdOn >= :startDate)
           AND (:endDate IS NULL OR c.createdOn <= :endDate)
           AND (:searchTerm IS NULL OR LOWER(c.groupName) LIKE LOWER(CONCAT('%', :searchTerm, '%')))
    """)
    Page<Client> findAllByBranchIdAndDateRangeAndSearchTerm(
        @Param("branchId") Long branchId,
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate,
        @Param("searchTerm") String searchTerm,
        Pageable pageable
    );

    // ✅ Simplified query for clients by branch with searchTerm support
    @Query("""
        SELECT c 
          FROM Client c 
         WHERE c.branch.id = :branchId
           AND (:searchTerm IS NULL OR LOWER(c.groupName) LIKE LOWER(CONCAT('%', :searchTerm, '%')))
    """)
    Page<Client> findClientsByBranchIdWithSearchTerm(
        @Param("branchId") Long branchId,
        @Param("searchTerm") String searchTerm,
        Pageable pageable
    );

    // ✅ Count clients by branch only
    Long countByBranchId(@Param("branchId") Long branchId);

    // ✅ Count clients by branch and date range
    @Query("""
        SELECT COUNT(c)
          FROM Client c
         WHERE c.branch.id = :branchId
           AND c.createdOn BETWEEN :startDate AND :endDate
    """)
    Long countClientsByBranchIdAndDateRange(
        @Param("branchId") Long branchId,
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate
    );
    
    @Query("SELECT c FROM Client c WHERE LOWER(c.groupName) LIKE LOWER(CONCAT('%', :groupName, '%'))")
    Page<Client> findByGroupNameContainingIgnoreCase(@Param("groupName") String groupName, Pageable pageable);    
}
