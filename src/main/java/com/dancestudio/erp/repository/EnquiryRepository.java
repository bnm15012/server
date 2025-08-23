package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Enquiry;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EnquiryRepository extends JpaRepository<Enquiry, Long> {

        Page<Enquiry> findByBranchIdAndEnquiryDateBetweenAndNameContainingIgnoreCase(
                        Long branchId, Date startDate, Date endDate, String searchTerm, Pageable pageable);

        long countByBranchIdAndEnquiryDateBetweenAndNameContainingIgnoreCase(
                        Long branchId, Date startDate, Date endDate, String searchTerm);

        long countByBranchId(Long branchId);


        @Query("""
        SELECT e FROM Enquiry e WHERE e.branch.id = :branchId AND (:searchTerm IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')))
        """)
        Page<Enquiry> findByBranchId(Long branchId, Pageable pageable, String searchTerm);
}
