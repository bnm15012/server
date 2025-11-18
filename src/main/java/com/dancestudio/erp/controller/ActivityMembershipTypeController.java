package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.activity.ActivityMembershipTypeEntry;
import com.dancestudio.erp.response.ActivityMembershipTypeResponse;
import com.dancestudio.erp.service.ActivityMembershipTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activityMembershipType")
public class ActivityMembershipTypeController extends BaseController<ActivityMembershipTypeEntry, ActivityMembershipTypeResponse, Long> {

    @Autowired
    private ActivityMembershipTypeService activityMembershipTypeService;

    @Override
    public ResponseEntity<ActivityMembershipTypeResponse> add(@RequestBody ActivityMembershipTypeEntry entry) {
        return activityMembershipTypeService.add(entry);
    }

    @Override
    public ResponseEntity<ActivityMembershipTypeResponse> update(@PathVariable Long id,
                                                                 @RequestBody ActivityMembershipTypeEntry entry) {
        return activityMembershipTypeService.update(id, entry);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return activityMembershipTypeService.delete(id);
    }

    @Override
    public ResponseEntity<ActivityMembershipTypeResponse> get(@PathVariable Long id) {
        return activityMembershipTypeService.get(id);
    }

    // Example: Get all membership types by studioId
    @GetMapping("/getAll/{studioId}")
    public ResponseEntity<ActivityMembershipTypeResponse> getAllByActivity(@PathVariable Long studioId, 
        @RequestParam(defaultValue = "1") Integer page, 
        @RequestParam(defaultValue = "10") Integer size) {
        return activityMembershipTypeService.getAllByStudioId(studioId, page, size);
    }
}
