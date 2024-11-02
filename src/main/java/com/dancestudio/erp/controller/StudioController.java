package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.response.StudioResponse;
import com.dancestudio.erp.service.StudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/studios")
public class StudioController {

    @Autowired
    private StudioService studioService;

    @PostMapping("/add")
    public ResponseEntity<StudioResponse> addStudio(@RequestBody StudioEntry studioEntry) {
        return studioService.addStudio(studioEntry);
    }

    @PutMapping("/update/{studioId}")
    public ResponseEntity<StudioResponse> updateStudio(@PathVariable Long studioId, @RequestBody StudioEntry studioEntry) {
        return studioService.updateStudio(studioId, studioEntry);
    }

    @DeleteMapping("/delete/{studioId}")
    public ResponseEntity<Void> deleteStudio(@PathVariable Long studioId) {
        return studioService.deleteStudio(studioId);
    }

    @GetMapping("/get/{studioId}")
    public ResponseEntity<StudioResponse> getStudioById(@PathVariable Long studioId) {
        return studioService.getStudioById(studioId);
    }

    @GetMapping("/getAllStudios")
    public ResponseEntity<StudioResponse> getAllStudios() {
        return studioService.getAllStudios();
    }
}
