package com.dancestudio.erp.service.impl;


import com.dancestudio.erp.entry.ActivityEntry;
import com.dancestudio.erp.manager.ActivityManager;
import com.dancestudio.erp.response.ActivityResponse;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.service.ActivityService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Setter(onMethod = @__({@Autowired}))
@Component
public class ActivityServiceImpl implements ActivityService {

    private ActivityManager activityManager;

    @Override
    public ActivityResponse addActivity(ActivityEntry activityEntry) {
        ActivityResponse response = new ActivityResponse();

        ActivityEntry entry = activityManager.addActivity(activityEntry);
        response.setData(Collections.singletonList(entry));
        response.setStatus(new StatusResponse(1, StatusResponse.Type.SUCCESS, Objects.isNull(entry) ? 0 : 1));

        return response;
    }

    @Override
    public ActivityResponse updateActivity(Long activityId, ActivityEntry activityEntry) {
        ActivityResponse response = new ActivityResponse();

        ActivityEntry entry = activityManager.updateActivity(activityId, activityEntry);
        response.setData(Collections.singletonList(entry));
        response.setStatus(new StatusResponse(1, StatusResponse.Type.SUCCESS, Objects.isNull(entry) ? 0 : 1));

        return response;
    }

    @Override
    public void deleteActivity(Long activityId) {
        activityManager.deleteActivity(activityId);
    }

    @Override
    public ActivityResponse getActivityById(Long activityId) {
        ActivityResponse response = new ActivityResponse();

        ActivityEntry entry = activityManager.getActivityById(activityId);
        response.setData(Collections.singletonList(entry));
        response.setStatus(new StatusResponse(1, StatusResponse.Type.SUCCESS, Objects.isNull(entry) ? 0 : 1));

        return response;
    }

    @Override
    public ActivityResponse getAllActivities() {
        ActivityResponse response = new ActivityResponse();

        List<ActivityEntry> entry = activityManager.getAllActivities();
        response.setData(entry);
        response.setStatus(new StatusResponse(1, StatusResponse.Type.SUCCESS, Objects.isNull(entry) ? 0 : entry.size()));

        return response;
    }
}
