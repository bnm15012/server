package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.ConditionsEntry;
import com.dancestudio.erp.response.ConditionsResponse;
import org.springframework.http.ResponseEntity;

public interface ConditionsService extends BaseService<ConditionsEntry, ConditionsResponse, Long> {

    ResponseEntity<ConditionsResponse> getAllByBranchId(Long branchId, String entityType, String activityType, int page, int size);
}
