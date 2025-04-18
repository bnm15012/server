package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.ActivityEntry;
import com.dancestudio.erp.response.ActivityResponse;
import com.dancestudio.erp.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activities")
public class ActivityController extends BaseController<ActivityEntry, ActivityResponse, Long> {

    @Autowired
    private ActivityService activityService;

    @Override
    public ResponseEntity<ActivityResponse> add(@RequestBody ActivityEntry activityEntry) {
        return activityService.add(activityEntry);
    }

    @Override
    public ResponseEntity<ActivityResponse> update(@PathVariable Long id, @RequestBody ActivityEntry activityEntry) {
        return activityService.update(id, activityEntry);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return activityService.delete(id);
    }

    @Override
    public ResponseEntity<ActivityResponse> get(@PathVariable Long id) {
        return activityService.get(id);
    }

    @GetMapping("/getAllActivities/{studioId}")
    public ResponseEntity<ActivityResponse> getAll(@PathVariable Long studioId) {
        return activityService.getAllActivities(studioId);
    }
}
