package com.dancestudio.erp.repository.Activity;

import com.dancestudio.erp.entity.activity.ActivityMembershipType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityMembershipTypeRepository extends JpaRepository<ActivityMembershipType, Long> {

    @Query("SELECT a FROM ActivityMembershipType a WHERE a.studio.id = :studioId")
    Page<ActivityMembershipType> findByStudioId(@Param("studioId") Long studioId, Pageable pageable);

}
