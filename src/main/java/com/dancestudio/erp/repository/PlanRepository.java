package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    List<Plan> findByCountryCode(String countryCode);

    Plan findByPlanTypeAndCountryCode(String planType, String countryCode);

}