package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.ConditionsEntry;

import java.util.List;

public interface ConditionsManager extends BaseManager<ConditionsEntry, Long> {

    List<ConditionsEntry> getAllConditionsByBranchId(Long branchId, String entityType, String activityType, Integer page, Integer size) throws Exception;
    
    long countByFilters(Long branchId, String entityType, String activityType) throws Exception;
}
