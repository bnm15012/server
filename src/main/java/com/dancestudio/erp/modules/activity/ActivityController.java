package com.dancestudio.erp.modules.activity;

import com.dancestudio.erp.base.BaseController;
import com.dancestudio.erp.base.BaseResponse;
import com.dancestudio.erp.base.BaseService;
import com.dancestudio.erp.entry.activity.ActivityEntry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activities")
public class ActivityController extends BaseController<ActivityEntry, Long> {

    @Autowired
    private ActivityService activityService;

    @Override
    protected BaseService<ActivityEntry, Long> getService() {
        return activityService;
    }

    @GetMapping("/getAll/{branchId}")
    public ResponseEntity<BaseResponse<ActivityEntry>> getAll(@PathVariable Long branchId) {
        return activityService.getAllActivities(branchId);
    }
}
