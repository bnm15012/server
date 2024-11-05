package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @Query("SELECT s FROM Activity s WHERE s.studioId = :studioId")
    List<Activity> findAllByStudioId(@Param("studioId") Long studioId);

}
