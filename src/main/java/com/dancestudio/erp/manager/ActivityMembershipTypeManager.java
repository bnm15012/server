package com.dancestudio.erp.manager;

import java.util.List;

import com.dancestudio.erp.entry.activity.ActivityMembershipTypeEntry;

public interface ActivityMembershipTypeManager extends BaseManager<ActivityMembershipTypeEntry, Long> {

    List<ActivityMembershipTypeEntry> getAllByStudioId(Long activityId) throws Exception;
}
