package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.activity.ActivityEntry;
import com.dancestudio.erp.response.ActivityResponse;
import com.dancestudio.erp.service.ActivityService;
import com.dancestudio.erp.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activities")
public class ActivityController extends BaseController<ActivityEntry, ActivityResponse, Long> {

    @Autowired
    private ActivityService activityService;


    @Override
    protected BaseService<ActivityEntry, ActivityResponse, Long> getService() {
        return activityService;
    }

    @GetMapping("/getAll/{branchId}")
    public ResponseEntity<ActivityResponse> getAll(@PathVariable Long branchId) {
        return activityService.getAllActivities(branchId);
    }
}
