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

    @Query("SELECT s FROM Subscription s WHERE s.branch.id = :branchId")
    List<Subscription> findByBranchId(@Param("branchId") Long branchId);

    @Query("SELECT s FROM Subscription s WHERE s.orderId = :orderId")
    Optional<Subscription> findByOrderId(@Param("orderId") String orderId);

    @Query("""
    SELECT s FROM Subscription s WHERE s.branch.id = :branchId AND s.status = 'ACTIVE' AND s.endDate = (
        SELECT MAX(sp.endDate) FROM Subscription sp WHERE sp.branch.id = :branchId AND sp.status = 'ACTIVE') 
    AND s.endDate > CURRENT_TIMESTAMP""")
    List<Subscription> findLatestSubscriptionByBranchId(@Param("branchId") Long branchId);

    @Modifying
    @Transactional
    @Query("UPDATE Subscription s SET s.status = :status WHERE s.createdOn < :createdAt AND s.status = 'CREATED'")
    int updateStatusByCreatedAtBefore(@Param("status") String status, @Param("createdAt") Date createdAt);
}
