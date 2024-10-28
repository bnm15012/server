package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.response.StudioResponse;
import com.dancestudio.erp.service.StudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/studios")
public class StudioController {

    @Autowired
    private StudioService studioService;

    @PostMapping
    public StudioResponse addStudio(@RequestBody StudioEntry studioEntry) {
        return studioService.addStudio(studioEntry);
    }

    @PutMapping("/{studioId}")
    public StudioResponse updateStudio(@PathVariable Long studioId, @RequestBody StudioEntry studioEntry) {
        return studioService.updateStudio(studioId, studioEntry);
    }

    @DeleteMapping("/{studioId}")
    public void deleteStudio(@PathVariable Long studioId) {
        studioService.deleteStudio(studioId);
    }

    @GetMapping("/{studioId}")
    public StudioResponse getStudioById(@PathVariable Long studioId) {
        return studioService.getStudioById(studioId);
    }

    @GetMapping
    public StudioResponse getAllStudios() {
        return studioService.getAllStudios();
    }
}
