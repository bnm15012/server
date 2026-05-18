package com.dancestudio.erp.modules.template.template;

import com.dancestudio.erp.base.BaseManager;
import com.dancestudio.erp.entity.Studio;
import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.repository.StudioRepository;
import com.dancestudio.erp.util.ConvertToEntryUtil;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.dancestudio.erp.constants.TemplateName.PAYMENT_REMINDER;
import static com.dancestudio.erp.constants.TemplateName.STUDIO_CLOSED_NOTICE;

@Service
public class TemplateManager extends BaseManager<Template, Long, TemplateEntry> {

    private final TemplateRepository templateRepository;
    @Autowired
    private StudioRepository studioRepository;

    public TemplateManager(TemplateRepository templateRepository) {
        super(templateRepository, "Template");
        this.templateRepository = templateRepository;
    }

    public TemplateEntry getTemplateDetails(String templateName) {
        Optional<Template> template = Optional.ofNullable(templateRepository.findByName(templateName));
        if (template.isEmpty()) {
            throw new RuntimeException("No default template available named:" + templateName);
        }
        return template.map(TemplateConvertor::convertToEntry).orElse(null);
    }

    public List<TemplateEntry> getAllTemplates(Long studioId) throws Exception {
        List<Template> templates = templateRepository.findAll();

        templates = templates.stream().filter(template -> PAYMENT_REMINDER.equals(template.getName()) ||
                STUDIO_CLOSED_NOTICE.equals(template.getName()))
                .toList();

        Studio studio = studioRepository.findById(studioId).get();
        StudioEntry studioEntry = ConvertToEntryUtil.convertToEntry(studio);

        return templates.stream()
                .map(template -> {
                    // Replace {studio_name} with studioName
                    String updatedBody = template.getBody().replace("{studio_name}", studioEntry.getStudioName());
                    template.setBody(updatedBody);
                    return TemplateConvertor.convertToEntry(template);
                })
                .collect(Collectors.toList());
    }

    @Override
    protected Template toEntity(TemplateEntry entry, Template existing)
            throws EntityNotFoundException, BeansException, Exception {
        return TemplateConvertor.convertToEntity(entry, existing);
    }

    @Override
    protected TemplateEntry toEntry(Template entity, String[] fields) throws EntityNotFoundException {
        return TemplateConvertor.convertToEntry(entity);
    }

}
