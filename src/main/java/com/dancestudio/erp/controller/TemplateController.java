package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.TemplateEntry;
import com.dancestudio.erp.response.TemplateResponse;
import com.dancestudio.erp.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/template")
public class TemplateController extends BaseController<TemplateEntry, TemplateResponse, Long> {

    @Autowired
    private TemplateService templateService;

    @Override
    public ResponseEntity<TemplateResponse> add(@RequestBody TemplateEntry templateEntry) {
        return templateService.add(templateEntry);
    }

    @Override
    public ResponseEntity<TemplateResponse> update(@PathVariable Long id, @RequestBody TemplateEntry templateEntry) {
        return templateService.update(id, templateEntry);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return templateService.delete(id);
    }

    @Override
    public ResponseEntity<TemplateResponse> get(@PathVariable Long id) {
        return templateService.get(id);
    }
}
