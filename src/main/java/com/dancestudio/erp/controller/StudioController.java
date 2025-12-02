package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.response.StudioResponse;
import com.dancestudio.erp.service.BaseService;
import com.dancestudio.erp.service.StudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/studios")
public class StudioController extends BaseController<StudioEntry, StudioResponse, Long> {

    @Autowired
    private StudioService studioService;

    @GetMapping("/getAll")
    public ResponseEntity<StudioResponse> getAllStudios() {
        return studioService.getAllStudios();
    }

    @Override
    protected BaseService<StudioEntry, StudioResponse, Long> getService() {
        return studioService;
    }
}
