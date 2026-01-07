package com.dancestudio.erp.modules.template.genericTemplate;

import java.util.Objects;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.dancestudio.erp.entity.Studio;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.repository.StudioRepository;
import jakarta.annotation.PostConstruct;

@Component
public class GenericTemplateConvertor {
    private static ApplicationContext applicationContext;

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        applicationContext = context;
    }

    public static GenericTemplate convertToEntity(GenricTemplateEntry entry, GenericTemplate existingConditions) throws BeansException, EntityNotFoundException {
        GenericTemplate genericTemplate = (existingConditions != null) ? existingConditions : new GenericTemplate();

        if (Objects.nonNull(entry.getTemplateType())) {
            genericTemplate.setTemplateType(entry.getTemplateType());
        }
        if (Objects.nonNull(entry.getTemplateName())) {
            genericTemplate.setTemplateName(entry.getTemplateName());
        }
        if (Objects.nonNull(entry.getTemplateSubject())) {
            genericTemplate.setTemplateSubject(entry.getTemplateSubject());
        }
        if (Objects.nonNull(entry.getTemplateContent())) {
            genericTemplate.setTemplateContent(entry.getTemplateContent());
        }
        if (Objects.nonNull(entry.getStudioId())) {
            Studio studio = applicationContext.getBean(StudioRepository.class).findById(entry.getStudioId())
                    .orElseThrow(() -> new EntityNotFoundException("Studio not found"));
            genericTemplate.setStudio(studio);
        }

        return genericTemplate;
    }

    public static GenricTemplateEntry convertToEntry(GenericTemplate genericTemplate) {
        GenricTemplateEntry entry = new GenricTemplateEntry();
        entry.setId(genericTemplate.getId());
        entry.setTemplateType(genericTemplate.getTemplateType());
        entry.setTemplateName(genericTemplate.getTemplateName());
        entry.setTemplateSubject(genericTemplate.getTemplateSubject());
        entry.setTemplateContent(genericTemplate.getTemplateContent());
        if (genericTemplate.getStudio() != null) {
            entry.setStudioId(genericTemplate.getStudio().getId());
        }
        return entry;
    }
}
