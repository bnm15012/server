package com.dancestudio.erp.repository.Activity;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dancestudio.erp.entity.activity.Activity;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Optional<Activity> findByActivityTypeAndBranchId(String activityType, Long branchId);

    List<Activity> findByBranchId(Long branchId);

}
