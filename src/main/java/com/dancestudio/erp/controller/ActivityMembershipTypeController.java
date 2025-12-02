package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.activity.ActivityMembershipTypeEntry;
import com.dancestudio.erp.response.ActivityMembershipTypeResponse;
import com.dancestudio.erp.service.ActivityMembershipTypeService;
import com.dancestudio.erp.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activityMembershipType")
public class ActivityMembershipTypeController extends BaseController<ActivityMembershipTypeEntry, ActivityMembershipTypeResponse, Long> {

    @Autowired
    private ActivityMembershipTypeService activityMembershipTypeService;

    @Override
    protected BaseService<ActivityMembershipTypeEntry, ActivityMembershipTypeResponse, Long> getService() {
        return activityMembershipTypeService;
    }

    @GetMapping("/getAll/{studioId}")
    public ResponseEntity<ActivityMembershipTypeResponse> getAll(@PathVariable Long studioId, 
        @RequestParam(defaultValue = "1") Integer page, 
        @RequestParam(defaultValue = "10") Integer size) {
        return activityMembershipTypeService.getAllByStudioId(studioId, page, size);
    }
}
