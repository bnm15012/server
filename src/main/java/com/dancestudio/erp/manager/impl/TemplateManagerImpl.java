package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.Studio;
import com.dancestudio.erp.entity.Template;
import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.entry.TemplateEntry;
import com.dancestudio.erp.enums.TemplateType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.TemplateManager;
import com.dancestudio.erp.repository.StudioRepository;
import com.dancestudio.erp.repository.TemplateRepository;
import com.dancestudio.erp.util.ConvertToEntryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.dancestudio.erp.constants.TemplateName.*;

@Service
public class TemplateManagerImpl implements TemplateManager {

    private final TemplateRepository templateRepository;
    @Autowired private StudioRepository studioRepository;

    @Autowired
    public TemplateManagerImpl(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @Override
    public TemplateEntry add(TemplateEntry templateEntry) throws EntityNotFoundException {
        Template template = convertToEntity(templateEntry, null);
        return convertToEntry(templateRepository.save(template));
    }

    @Override
    public TemplateEntry update(Long templateId, TemplateEntry templateEntry) throws EntityNotFoundException {
        Template existingTemplate = templateRepository.findById(templateId)
                .orElseThrow(() -> new EntityNotFoundException("Bank Account not found"));

        Template updatedTemplate = convertToEntity(templateEntry, existingTemplate);
        return convertToEntry(templateRepository.save(updatedTemplate));
    }

    @Override
    public void delete(Long templateId) throws EntityNotFoundException {
        templateRepository.findById(templateId)
                .orElseThrow(() -> new EntityNotFoundException("Bank Account not found"));

        templateRepository.deleteById(templateId);
    }

    @Override
    public TemplateEntry getById(Long templateId) throws EntityNotFoundException {
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new EntityNotFoundException("Bank Account not found"));

        return convertToEntry(template);
    }

    @Override
    public TemplateEntry getTemplateDetails(String templateName) {
        Optional<Template> template = Optional.ofNullable(templateRepository.findByName(templateName));
        return template.map(this::convertToEntry).orElse(null);
    }

    @Override
    public List<TemplateEntry> getAllTemplates(Long studioId) throws Exception {
        List<Template> templates = templateRepository.findAll();

        templates.removeIf(template -> template.getName().equalsIgnoreCase(UPDATE_STUDENT_EMAIL) ||
                template.getName().equalsIgnoreCase(ADD_NEW_STUDENT_EMAIL) ||
                template.getName().equalsIgnoreCase(ADD_NEW_STUDIO_EMAIL) ||
                template.getName().equalsIgnoreCase(UPDATE_STUDIO_EMAIL) ||
                template.getName().equalsIgnoreCase(SUBSCRIPTION_RENEWAL_REMINDER));

        Studio studio = studioRepository.findById(studioId).get();
        StudioEntry studioEntry = ConvertToEntryUtil.convertToEntry(studio);

        return templates.stream()
                .map(template -> {
                    // Replace {studio_name} with studioName
                    String updatedBody = template.getBody().replace("{studio_name}", studioEntry.getStudioName());
                    template.setBody(updatedBody);
                    return convertToEntry(template);
                })
                .collect(Collectors.toList());
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

    public static Template convertToEntity(TemplateEntry templateEntry, Template existingTemplate) throws EntityNotFoundException {
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
