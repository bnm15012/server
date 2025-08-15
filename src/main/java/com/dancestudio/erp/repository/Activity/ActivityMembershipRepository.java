package com.dancestudio.erp.repository.Activity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.dancestudio.erp.entity.activity.ActivityMembershipPlan;

public interface ActivityMembershipRepository extends JpaRepository<ActivityMembershipPlan, Long> {

}
