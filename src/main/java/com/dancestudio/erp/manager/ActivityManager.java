package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.ActivityEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;

import java.util.List;

public interface ActivityManager extends BaseManager<ActivityEntry, Long> {

    List<ActivityEntry> getAllActivities(Long studioId) throws EntityNotFoundException;
}
