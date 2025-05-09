package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Booking;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("""
        SELECT s 
          FROM Booking s 
         WHERE s.branch.id = :branchId 
           AND s.createdOn BETWEEN :startDate AND :endDate
    """)
    Page<Booking> findAllBookingsByBranchIdAndDateRange(
        @Param("branchId") Long branchId,
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate,
        Pageable pageable
    );
    
    @Query("SELECT s FROM Booking s WHERE s.branch.id = :branchId")
    Page<Booking> findBookingsByBranchId(@Param("branchId") Long branchId, Pageable pageable);

    @Query("SELECT COUNT(s) FROM Booking s WHERE s.branch.id = :branchId")
    Long countBookingsByBranchId(@Param("branchId") Long branchId);

    @Query("""
                SELECT COUNT(s)
                  FROM Booking s
                 WHERE s.branch.id = :branchId
                   AND s.createdOn BETWEEN :startDate AND :endDate
            """)
    Long countBookingsByBranchIdAndDateRange(
            @Param("branchId") Long branchId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

}