package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.GenricTemplateEntry;
import com.dancestudio.erp.response.GenericTemplateResponse;
import com.dancestudio.erp.service.GenricTemplateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/genericTemplate")
public class GenricTemplateController extends BaseController<GenricTemplateEntry, GenericTemplateResponse, Long> {

    @Autowired
    private GenricTemplateService genericTemplateService;

    @Override
    public ResponseEntity<GenericTemplateResponse> add(@RequestBody GenricTemplateEntry genericTemplateEntry) {
        return genericTemplateService.add(genericTemplateEntry);
    }

    @Override
    public ResponseEntity<GenericTemplateResponse> update(@PathVariable Long id, @RequestBody GenricTemplateEntry genericTemplateEntry) {
        return genericTemplateService.update(id, genericTemplateEntry);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return genericTemplateService.delete(id);
    }

    @Override
    public ResponseEntity<GenericTemplateResponse> get(@PathVariable Long id) {
        return genericTemplateService.get(id);
    }

    @GetMapping("/getAll/{studioId}")
    public ResponseEntity<GenericTemplateResponse> getAllConditions(
            @PathVariable Long studioId,
            @RequestParam(required = false) String templateType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return genericTemplateService.getAllByStudioId(studioId, templateType, page, size);
    }
}
