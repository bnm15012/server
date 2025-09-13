package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    @Query("SELECT p FROM Plan p " +
        "WHERE p.countryCode = :countryCode " +
        "AND ((:AMC = TRUE AND p.planType = 'AMC') OR (:AMC = FALSE AND p.planType <> 'AMC'))")
    List<Plan> findPlansByCountryCodeAndAmcFlag(
            @Param("countryCode") String countryCode,
            @Param("AMC") Boolean AMC
    );

    Plan findByPlanTypeAndCountryCode(String planType, String countryCode);

}