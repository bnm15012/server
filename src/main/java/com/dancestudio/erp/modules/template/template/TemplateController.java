package com.dancestudio.erp.modules.template.template;
import com.dancestudio.erp.base.BaseController;
import com.dancestudio.erp.base.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/template")
public class TemplateController extends BaseController<TemplateEntry,  Long> {

    @Autowired
    private TemplateService templateService;

    @Override
    protected BaseService<TemplateEntry, Long> getService() {
        return templateService;
    }

}
