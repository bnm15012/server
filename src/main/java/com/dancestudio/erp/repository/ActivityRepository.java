package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
