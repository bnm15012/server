package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.response.StudioResponse;
import com.dancestudio.erp.service.StudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/studios")
public class StudioController extends BaseController<StudioEntry, StudioResponse, Long> {

    @Autowired
    private StudioService studioService;

    @Override
    public ResponseEntity<StudioResponse> add(@RequestBody StudioEntry studioEntry) {
        return studioService.add(studioEntry);
    }

    @Override
    public ResponseEntity<StudioResponse> update(@PathVariable Long id, @RequestBody StudioEntry studioEntry) {
        return studioService.update(id, studioEntry);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return studioService.delete(id);
    }

    @Override
    public ResponseEntity<StudioResponse> get(@PathVariable Long id) {
        return studioService.get(id);
    }

    @GetMapping("/getAllStudios")
    public ResponseEntity<StudioResponse> getAllStudios() {
        return studioService.getAllStudios();
    }
}
