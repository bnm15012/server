package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.activity.ActivityEntry;
import com.dancestudio.erp.response.ActivityResponse;
import org.springframework.http.ResponseEntity;

public interface ActivityService extends BaseService<ActivityEntry, ActivityResponse, Long> {

    ResponseEntity<ActivityResponse> getAllActivities(Long branchId);
}
