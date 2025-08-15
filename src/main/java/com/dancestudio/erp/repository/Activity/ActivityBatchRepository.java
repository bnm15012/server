package com.dancestudio.erp.repository.Activity;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dancestudio.erp.entity.activity.ActivityBatch;
public interface ActivityBatchRepository extends JpaRepository<ActivityBatch, Long> {

}
