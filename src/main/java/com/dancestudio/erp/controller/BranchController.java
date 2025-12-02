package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.BranchEntry;
import com.dancestudio.erp.response.BranchResponse;
import com.dancestudio.erp.service.BaseService;
import com.dancestudio.erp.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/branch")
public class BranchController extends BaseController<BranchEntry, BranchResponse, Long> {

    @Autowired
    private BranchService branchService;

    @Override
    protected BaseService<BranchEntry, BranchResponse, Long> getService() {
        return branchService;
    }

    @GetMapping("/getAll/{studioId}")
    public ResponseEntity<BranchResponse> getAllBranchesOfStudio(@PathVariable Long studioId) throws Exception {
        return branchService.getAllBranchesOfStudio(studioId);
    }

    @PutMapping("/enableDisable/{branchId}/{flag}")
    public ResponseEntity<BranchResponse> enableDisableBranch(@PathVariable Long branchId, @PathVariable boolean flag)
            throws Exception {
        return branchService.enableDisableBranch(branchId, flag);
    }

}
