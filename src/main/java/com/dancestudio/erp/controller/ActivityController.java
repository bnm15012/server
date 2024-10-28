package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.ActivityEntry;
import com.dancestudio.erp.response.ActivityResponse;
import com.dancestudio.erp.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping
    public ActivityResponse addActivity(@RequestBody ActivityEntry activityEntry) {
        return activityService.addActivity(activityEntry);
    }

    @PutMapping("/{activityId}")
    public ActivityResponse updateActivity(@PathVariable Long activityId, @RequestBody ActivityEntry activityEntry) {
        return activityService.updateActivity(activityId, activityEntry);
    }

    @DeleteMapping("/{activityId}")
    public void deleteActivity(@PathVariable Long activityId) {
        activityService.deleteActivity(activityId);
    }

    @GetMapping("/{activityId}")
    public ActivityResponse getActivityById(@PathVariable Long activityId) {
        return activityService.getActivityById(activityId);
    }

    @GetMapping
    public ActivityResponse getAllActivities() {
        return activityService.getAllActivities();
    }
}
