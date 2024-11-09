package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.Template;
import com.dancestudio.erp.entry.TemplateEntry;
import com.dancestudio.erp.enums.TemplateType;
import com.dancestudio.erp.manager.TemplateManager;
import com.dancestudio.erp.repository.TemplateRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@Setter(onMethod = @__({@Autowired}))
public class TemplateManagerImpl implements TemplateManager {

    private TemplateRepository templateRepository;

    @Override
    public TemplateEntry getTemplateDetails(String templateName) {
        Optional<Template> template = Optional.ofNullable(templateRepository.findByName(templateName));
        return template.map(this::convertToEntry).orElse(null);
    }

    private TemplateEntry convertToEntry(Template entity) {
        if (entity == null) {
            return null;
        }

        TemplateEntry entry = new TemplateEntry();
        entry.setId(entity.getId());
        entry.setTemplateName(entity.getName());
        entry.setTemplateBody(entity.getBody());
        entry.setSubject(entity.getSubject());
        entry.setTemplateType(TemplateType.valueOf(entity.getTemplateType()));
        return entry;
    }


}
