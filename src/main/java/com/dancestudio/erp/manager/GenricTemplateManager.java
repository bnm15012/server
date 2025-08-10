package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.GenricTemplateEntry;

import java.util.List;

public interface GenricTemplateManager extends BaseManager<GenricTemplateEntry, Long> {

    List<GenricTemplateEntry> getAllConditionsByBranchId(Long branchId, String templateType, Integer page, Integer size) throws Exception;
    
    long countByFilters(Long branchId, String templateType) throws Exception;
}
