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

    @GetMapping("/getAllConditions/{branchId}")
    public ResponseEntity<ConditionsResponse> getAllConditions(
            @PathVariable Long branchId,
            @RequestParam(required = false) String entityType,
            @RequestParam(required = false) String activityType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return conditionsService.getAllByBranchId(branchId, entityType, activityType, page, size);
    }
}
