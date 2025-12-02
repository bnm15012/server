package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.GenricTemplateEntry;
import com.dancestudio.erp.response.GenericTemplateResponse;
import com.dancestudio.erp.service.BaseService;
import com.dancestudio.erp.service.GenricTemplateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/genericTemplate")
public class GenricTemplateController extends BaseController<GenricTemplateEntry, GenericTemplateResponse, Long> {

    @Autowired
    private GenricTemplateService genericTemplateService;

    @GetMapping("/getAll/{studioId}")
    public ResponseEntity<GenericTemplateResponse> getAllConditions(
            @PathVariable Long studioId,
            @RequestParam(required = false, name = "searchTerm") String templateType,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return genericTemplateService.getAllByStudioId(studioId, templateType, page, size);
    }

    @Override
    protected BaseService<GenricTemplateEntry, GenericTemplateResponse, Long> getService() {
        return genericTemplateService;
    }
}
