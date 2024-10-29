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

    @PostMapping("/add")
    public ActivityResponse addActivity(@RequestBody ActivityEntry activityEntry) {
        return activityService.addActivity(activityEntry);
    }

    @PutMapping("/update/{activityId}")
    public ActivityResponse updateActivity(@PathVariable Long activityId, @RequestBody ActivityEntry activityEntry) {
        return activityService.updateActivity(activityId, activityEntry);
    }

    @DeleteMapping("/delete/{activityId}")
    public void deleteActivity(@PathVariable Long activityId) {
        activityService.deleteActivity(activityId);
    }

    @GetMapping("/get/{activityId}")
    public ActivityResponse getActivityById(@PathVariable Long activityId) {
        return activityService.getActivityById(activityId);
    }

    @GetMapping("/getAllActivities/{studioId}")
    public ActivityResponse getAllActivities(@PathVariable Long studioId) {
        return activityService.getAllActivities(studioId);
    }
}
