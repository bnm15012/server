package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT s FROM Booking s WHERE s.studio.id = :studioId and month(s.createdOn) >= :startMonth and month(s.createdOn) <= :endMonth")
    Page<Booking> findAllByStudioId(@Param("studioId") Long studioId, @Param("startMonth") Long startMonth, @Param("endMonth") Long endMonth, Pageable pageable);

    @Query("SELECT s FROM Booking s WHERE s.studio.id = :studioId")
    Page<Booking> findBookingsByStudioId(@Param("studioId") Long studioId, Pageable pageable);

    @Query("SELECT COUNT(s) FROM Booking s WHERE s.studio.id = :studioId")
    Long countBookingsByStudioId(@Param("studioId") Long studioId);

    @Query("SELECT COUNT(s) FROM Booking s WHERE s.studio.id = :studioId and month(s.createdOn) >= :startMonth and month(s.createdOn) <= :endMonth")
    Long countBookingsByStudioIdAndMonthLong(@Param("studioId") Long studioId, @Param("startMonth") Long startMonth, @Param("endMonth") Long endMonth);

}