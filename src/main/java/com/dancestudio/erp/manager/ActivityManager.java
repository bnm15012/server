package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.ActivityEntry;

import java.util.List;

public interface ActivityManager extends BaseManager<ActivityEntry, Long> {

    List<ActivityEntry> getAllActivities(Long branchId) throws Exception;
}
