package com.dancestudio.erp.modules.template.template;

import org.springframework.stereotype.Component;

import com.dancestudio.erp.entry.TemplateEntry;
import com.dancestudio.erp.enums.TemplateType;

@Component
public class TemplateConvertor {

    public static TemplateEntry convertToEntry(Template entity) {
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

    public static Template convertToEntity(TemplateEntry templateEntry, Template existingTemplate) {
        if (templateEntry == null) {
            return null;
        }

        Template template = existingTemplate != null ? existingTemplate : new Template();
        template.setName(templateEntry.getTemplateName());
        template.setBody(templateEntry.getTemplateBody());
        template.setSubject(templateEntry.getSubject());
        template.setTemplateType(templateEntry.getTemplateType().name());

        return template;
    }
}
