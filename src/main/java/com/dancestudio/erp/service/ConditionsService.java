package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.ConditionsEntry;
import com.dancestudio.erp.response.ConditionsResponse;
import org.springframework.http.ResponseEntity;

public interface ConditionsService extends BaseService<ConditionsEntry, ConditionsResponse, Long> {
    ResponseEntity<ConditionsResponse> getByEntityTypeAndBranchId(String entityType, Long branchId);
    
    ResponseEntity<ConditionsResponse> getAllByBranchId(Long branchId, Integer page, Integer size);
    
    ResponseEntity<ConditionsResponse> countAllByBranchId(Long branchId);
}
