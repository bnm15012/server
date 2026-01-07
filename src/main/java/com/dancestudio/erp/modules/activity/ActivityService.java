package com.dancestudio.erp.modules.activity;

import com.dancestudio.erp.base.BaseResponse;
import com.dancestudio.erp.base.BaseService;
import com.dancestudio.erp.entry.activity.ActivityEntry;
import com.dancestudio.erp.modules.activity.manager.ActivityManager;
import com.dancestudio.erp.response.StatusResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter(onMethod = @__({ @Autowired }))
@Component
public class ActivityService extends BaseService<ActivityEntry, Long> {

    private ActivityManager activityManager;

    public ResponseEntity<BaseResponse<ActivityEntry>> getAllActivities(Long branchId) {
        BaseResponse<ActivityEntry> response = new BaseResponse<ActivityEntry>();

        try {
            List<ActivityEntry> entries = activityManager.getAllActivities(branchId);
            response.setData(entries);
            response.setStatus(new StatusResponse(1, "Activities retrieved successfully", StatusResponse.Type.SUCCESS,
                    entries.size()));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    protected ActivityEntry doAdd(ActivityEntry entry) throws Exception {
        return activityManager.add(entry);
    }

    @Override
    protected ActivityEntry doUpdate(Long id, ActivityEntry entry) throws Exception {
        return activityManager.update(id, entry);
    }

    @Override
    protected void doDelete(Long id) throws Exception {
        activityManager.delete(id);
    }

    @Override
    protected ActivityEntry doGet(Long id) throws Exception {
        return activityManager.getById(id);
    }
}
