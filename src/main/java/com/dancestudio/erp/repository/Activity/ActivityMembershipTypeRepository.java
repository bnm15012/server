package com.dancestudio.erp.repository.Activity;

import com.dancestudio.erp.entity.activity.ActivityMembershipType;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityMembershipTypeRepository extends JpaRepository<ActivityMembershipType, Long> {
    List<ActivityMembershipType> findByStudioId(Long studioId);
}
