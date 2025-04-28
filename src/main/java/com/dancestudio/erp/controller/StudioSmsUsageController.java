package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.StudioSmsUsageEntry;
import com.dancestudio.erp.response.StudioSmsUsageResponse;
import com.dancestudio.erp.service.StudioSmsUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/studioSmsUsage")
public class StudioSmsUsageController extends BaseController<StudioSmsUsageEntry, StudioSmsUsageResponse, Long> {

    @Autowired
    private StudioSmsUsageService studioSmsUsageService;

    @Override
    public ResponseEntity<StudioSmsUsageResponse> add(@RequestBody StudioSmsUsageEntry studioSmsUsageEntry) {
        return studioSmsUsageService.add(studioSmsUsageEntry);
    }

    @Override
    public ResponseEntity<StudioSmsUsageResponse> update(@PathVariable Long id, @RequestBody StudioSmsUsageEntry studioSmsUsageEntry) {
        return studioSmsUsageService.update(id, studioSmsUsageEntry);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return studioSmsUsageService.delete(id);
    }

    @Override
    public ResponseEntity<StudioSmsUsageResponse> get(@PathVariable Long id) {
        return studioSmsUsageService.get(id);
    }
}
