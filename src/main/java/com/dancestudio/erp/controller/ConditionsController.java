package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.ConditionsEntry;
import com.dancestudio.erp.response.ConditionsResponse;
import com.dancestudio.erp.service.ConditionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/conditions")
public class ConditionsController extends BaseController<ConditionsEntry, ConditionsResponse, Long> {

    @Autowired
    private ConditionsService conditionsService;

    @Override
    public ResponseEntity<ConditionsResponse> add(@RequestBody ConditionsEntry conditionsEntry) {
        return conditionsService.add(conditionsEntry);
    }

    @Override
    public ResponseEntity<ConditionsResponse> update(@PathVariable Long id, @RequestBody ConditionsEntry conditionsEntry) {
        return conditionsService.update(id, conditionsEntry);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return conditionsService.delete(id);
    }

    @Override
    public ResponseEntity<ConditionsResponse> get(@PathVariable Long id) {
        return conditionsService.get(id);
    }

    @GetMapping("/entityType/{entityType}/branch/{branchId}")
    public ResponseEntity<ConditionsResponse> getByEntityTypeAndBranchId(
            @PathVariable String entityType,
            @PathVariable Long branchId) {
        return conditionsService.getByEntityTypeAndBranchId(entityType, branchId);
    }
    
    @GetMapping("/branch/{branchId}")
    public ResponseEntity<ConditionsResponse> getAllByBranchId(
            @PathVariable Long branchId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return conditionsService.getAllByBranchId(branchId, page, size);
    }
    
    @GetMapping("/branch/{branchId}/count")
    public ResponseEntity<ConditionsResponse> countAllByBranchId(
            @PathVariable Long branchId) {
        return conditionsService.countAllByBranchId(branchId);
    }
}
