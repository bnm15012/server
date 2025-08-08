package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.ConditionsEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;

import java.util.List;

public interface ConditionsManager extends BaseManager<ConditionsEntry, Long> {
    ConditionsEntry getByEntityTypeAndBranchId(String entityType, Long branchId) throws EntityNotFoundException;
    
    List<ConditionsEntry> getAllByBranchId(Long branchId, Integer page, Integer size) throws Exception;
    
    long countAllByBranchId(Long branchId) throws Exception;
}
