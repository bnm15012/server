package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.TemplateEntry;
import com.dancestudio.erp.response.TemplateResponse;
import com.dancestudio.erp.service.BaseService;
import com.dancestudio.erp.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/template")
public class TemplateController extends BaseController<TemplateEntry, TemplateResponse, Long> {

    @Autowired
    private TemplateService templateService;

    @Override
    protected BaseService<TemplateEntry, TemplateResponse, Long> getService() {
        return templateService;
    }

}
