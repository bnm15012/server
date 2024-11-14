package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Long> {

    @Query(value = "select s from subscription_plan s where s.studio.id = :studioId", nativeQuery = true)
    List<SubscriptionPlan> findByStudioId(@Param("studioId") Long studioId);

    @Query(value = "SELECT MAX(s.end_date) FROM subscription_plan s WHERE s.studio_id = :studioId AND (s.status = 'ACTIVE' OR (s.subscription_plan = 'TRIAL' AND s.end_date > CURRENT_TIMESTAMP))", nativeQuery = true)
    Date findMaxEndDateByStudioId(@Param("studioId") Long studioId);

}
