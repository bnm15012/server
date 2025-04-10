package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Subscription;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("SELECT s FROM Subscription s WHERE s.studio.id = :studioId")
    List<Subscription> findByStudioId(@Param("studioId") Long studioId);

    @Query("SELECT s FROM Subscription s WHERE s.orderId = :orderId")
    Optional<Subscription> findByOrderId(@Param("orderId") String orderId);

    @Query("""
        SELECT s FROM Subscription s WHERE s.studio.id = :studioId AND s.endDate = (
        SELECT MAX(sp.endDate) FROM Subscription sp WHERE sp.studio.id = :studioId)
        AND s.endDate > CURRENT_TIMESTAMP AND s.status = 'ACTIVE'""")
    Optional<Subscription> findLatestSubscriptionByStudioId(@Param("studioId") Long studioId);

    @Modifying
    @Transactional
    @Query("UPDATE Subscription s SET s.status = :status WHERE s.createdOn < :createdAt AND s.status = 'CREATED'")
    int updateStatusByCreatedAtBefore(@Param("status") String status, @Param("createdAt") Date createdAt);
}
