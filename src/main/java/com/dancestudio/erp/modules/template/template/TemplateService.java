package com.dancestudio.erp.modules.template.template;


import com.dancestudio.erp.base.BaseService;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Setter(onMethod = @__({@Autowired}))
@Component
public class TemplateService  extends BaseService<TemplateEntry, Long> {

    private TemplateManager templateManager;

    @Override
    protected TemplateEntry doAdd(TemplateEntry entry) throws Exception {
        return templateManager.add(entry);
    }

    @Override
    protected TemplateEntry doUpdate(Long id, TemplateEntry entry) throws Exception {
        return templateManager.update(id, entry);
    }

    @Override
    protected void doDelete(Long id) throws Exception {
        templateManager.delete(id);    
    }

    @Override
    protected TemplateEntry doGet(Long id) throws Exception {
        return templateManager.getById(id);
    }

}
