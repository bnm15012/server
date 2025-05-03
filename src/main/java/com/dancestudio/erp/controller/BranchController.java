package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.BranchEntry;
import com.dancestudio.erp.response.BranchResponse;
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
    public ResponseEntity<BranchResponse> add(@RequestBody BranchEntry branchEntry) {
        return branchService.add(branchEntry);
    }

    @Override
    public ResponseEntity<BranchResponse> update(@PathVariable Long id, @RequestBody BranchEntry branchEntry) {
        return branchService.update(id, branchEntry);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return branchService.delete(id);
    }

    @Override
    public ResponseEntity<BranchResponse> get(@PathVariable Long id) {
        return branchService.get(id);
    }

    @GetMapping("/getAllBranchesOfStudio/{studioId}")
    public ResponseEntity<BranchResponse> getAllBranchesOfStudio(@PathVariable Long studioId) throws Exception {
        return branchService.getAllBranchesOfStudio(studioId);
    }

    @PutMapping("/enableDisable/{branchId}/{flag}")
    public ResponseEntity<BranchResponse> enableDisableBranch(@PathVariable Long branchId, @PathVariable boolean flag) throws Exception {
        return branchService.enableDisableBranch(branchId, flag);
    }

}
