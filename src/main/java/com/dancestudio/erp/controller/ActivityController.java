package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.ActivityEntry;
import com.dancestudio.erp.response.ActivityResponse;
import com.dancestudio.erp.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping("/add")
    public ResponseEntity<ActivityResponse> addActivity(@RequestBody ActivityEntry activityEntry) {
        return activityService.addActivity(activityEntry);
    }

    @PutMapping("/update/{activityId}")
    public ResponseEntity<ActivityResponse> updateActivity(@PathVariable Long activityId, @RequestBody ActivityEntry activityEntry) {
        return activityService.updateActivity(activityId, activityEntry);
    }

    @DeleteMapping("/delete/{activityId}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long activityId) {
        return activityService.deleteActivity(activityId);
    }

    @GetMapping("/get/{activityId}")
    public ResponseEntity<ActivityResponse> getActivityById(@PathVariable Long activityId) {
        return activityService.getActivityById(activityId);
    }

    @GetMapping("/getAllActivities/{studioId}")
    public ResponseEntity<ActivityResponse> getAllActivities(@PathVariable Long studioId) {
        return activityService.getAllActivities(studioId);
    }
}
