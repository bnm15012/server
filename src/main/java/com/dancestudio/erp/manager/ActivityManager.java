package com.dancestudio.erp.manager;

import java.util.List;

import com.dancestudio.erp.entry.activity.ActivityEntry;


public interface ActivityManager extends BaseManager<ActivityEntry, Long> {

    List<ActivityEntry> getAllActivities(Long branchId) throws Exception;
}
