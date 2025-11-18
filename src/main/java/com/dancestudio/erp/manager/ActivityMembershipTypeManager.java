package com.dancestudio.erp.manager;

import org.springframework.data.domain.Page;

import com.dancestudio.erp.entity.activity.ActivityMembershipType;
import com.dancestudio.erp.entry.activity.ActivityMembershipTypeEntry;

public interface ActivityMembershipTypeManager extends BaseManager<ActivityMembershipTypeEntry, Long> {

   public Page<ActivityMembershipType> getAllByStudioId(Long studioId, Integer page, Integer size);
}
