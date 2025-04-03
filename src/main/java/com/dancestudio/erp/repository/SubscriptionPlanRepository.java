package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.SubscriptionPlan;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Long> {

    @Query("SELECT s FROM SubscriptionPlan s WHERE s.studio.id = :studioId")
    List<SubscriptionPlan> findByStudioId(@Param("studioId") Long studioId);

    @Query("SELECT s FROM SubscriptionPlan s WHERE s.orderId = :orderId")
    Optional<SubscriptionPlan> findByOrderId(@Param("orderId") String orderId);

    @Query("""
            SELECT s
            FROM SubscriptionPlan s
            WHERE s.studio.id = :studioId
            AND (s.status = 'ACTIVE' OR (s.subscriptionPlan = 'TRIAL' AND s.endDate > CURRENT_TIMESTAMP))
            ORDER BY s.endDate DESC
            LIMIT 1
            """)
    Optional<SubscriptionPlan> findLatestSubscriptionByStudioId(@Param("studioId") Long studioId);

    @Modifying
    @Transactional
    @Query("DELETE FROM SubscriptionPlan s WHERE s.status = :status AND s.createdOn < :createdAt")
    int deleteByStatusAndCreatedAtBefore(@Param("status") String status, @Param("createdAt") Date createdAt);

}
