package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.PlanEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface PlanManager extends BaseManager<PlanEntry, Long> {

    List<PlanEntry> getAllPlans(HttpServletRequest request) throws EntityNotFoundException;
}
