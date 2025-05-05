package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.entry.DashboardEntry;
import com.dancestudio.erp.manager.DashboardManager;
import com.dancestudio.erp.response.DashboardResponse;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.service.DashboardService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Setter(onMethod = @__({@Autowired}))
@Component
public class DashboardServiceImpl implements DashboardService {

    private DashboardManager dashboardManager;

    public DashboardResponse getDashboardDetails(Long studioId, int currentMonth, int currentYear) {
        DashboardResponse response = new DashboardResponse();
        try {
            DashboardEntry entry = dashboardManager.getDashboardDetails(studioId, currentMonth, currentYear);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Fetched dashboard details", StatusResponse.Type.SUCCESS));
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(1, ex.getMessage(), StatusResponse.Type.ERROR));
        }
        return response;
    }

}
