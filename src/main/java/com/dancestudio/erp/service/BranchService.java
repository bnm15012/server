package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.BranchEntry;
import com.dancestudio.erp.response.BranchResponse;
import org.springframework.http.ResponseEntity;

public interface BranchService extends BaseService<BranchEntry, BranchResponse, Long> {

    ResponseEntity<BranchResponse> getAllBranchesOfStudio(Long studioId) throws Exception;

    ResponseEntity<BranchResponse> enableDisableBranch(Long branchId, boolean flag) throws Exception;
}
