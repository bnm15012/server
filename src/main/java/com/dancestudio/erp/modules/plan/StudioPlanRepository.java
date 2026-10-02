package com.dancestudio.erp.modules.plan;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudioPlanRepository extends JpaRepository<StudioPlan, Long> {

  @Query("SELECT sp FROM StudioPlan sp WHERE sp.studio.id = :studioId")
  List<StudioPlan> findByStudioId(@Param("studioId") Long studioId);

  @Query(
      "SELECT sp FROM StudioPlan sp WHERE sp.studio.id = :studioId AND sp.plan.planType = :planType")
  Optional<StudioPlan> findByStudioIdAndPlanType(
      @Param("studioId") Long studioId, @Param("planType") String planType);
}
