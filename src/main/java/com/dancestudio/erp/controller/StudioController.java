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

    @PostMapping("/add")
    public StudioResponse addStudio(@RequestBody StudioEntry studioEntry) {
        return studioService.addStudio(studioEntry);
    }

    @PutMapping("/update/{studioId}")
    public StudioResponse updateStudio(@PathVariable Long studioId, @RequestBody StudioEntry studioEntry) {
        return studioService.updateStudio(studioId, studioEntry);
    }

    @DeleteMapping("/delete/{studioId}")
    public StudioResponse deleteStudio(@PathVariable Long studioId) {
        return studioService.deleteStudio(studioId);
    }

    @GetMapping("/get/{studioId}")
    public StudioResponse getStudioById(@PathVariable Long studioId) {
        return studioService.getStudioById(studioId);
    }

    @GetMapping("/getAllStudios")
    public StudioResponse getAllStudios() {
        return studioService.getAllStudios();
    }
}
