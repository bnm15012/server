package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Optional<Activity> findByActivityTypeAndStudioId(String activityType, Long studioId);

    @Query("SELECT s FROM Activity s WHERE s.studio.id = :studioId")
    List<Activity> findAllByStudioId(@Param("studioId") Long studioId);

}
