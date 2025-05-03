package com.dancestudio.erp.service;

import com.dancestudio.erp.response.DashboardResponse;

public interface DashboardService {

    DashboardResponse getDashboardDetails(Long studioId, int currentMonth, int currentYear, String userTimeZone);

}
