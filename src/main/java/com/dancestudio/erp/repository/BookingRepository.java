package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {
    // All filtering, counting, and searching handled via Specifications
}
