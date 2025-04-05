package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.DashboardEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;

public interface DashboardManager {

    DashboardEntry getDashboardDetails(Long studioId, Long startMonth, Long endMonth) throws EntityNotFoundException;

}