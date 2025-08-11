package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.GenericTemplate;
import com.dancestudio.erp.entry.GenricTemplateEntry;
import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.GenricTemplateManager;
import com.dancestudio.erp.manager.StudioManager;
import com.dancestudio.erp.repository.GenricTemplateRepository;
import com.dancestudio.erp.util.ConvertToEntryUtil;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Setter
public class GenricTemplateManagerImpl implements GenricTemplateManager {

    private final GenricTemplateRepository conditionsRepository;

    @Autowired
    private StudioManager studioManager;

    @Autowired
    public GenricTemplateManagerImpl(GenricTemplateRepository conditionsRepository) {
        this.conditionsRepository = conditionsRepository;
    }

    @Override
    @Transactional
    public GenricTemplateEntry add(GenricTemplateEntry genericTemplateEntry) throws Exception {
        GenericTemplate conditions = convertToEntity(genericTemplateEntry, null);
        return convertToEntry(conditionsRepository.save(conditions));
    }

    @Override
    @Transactional
    public GenricTemplateEntry update(Long id, GenricTemplateEntry genericTemplateEntry) throws Exception {
        GenericTemplate existingConditions = conditionsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Conditions not found"));

        GenericTemplate updatedConditions = convertToEntity(genericTemplateEntry, existingConditions);
        return convertToEntry(conditionsRepository.save(updatedConditions));
    }

    @Override
    @Transactional
    public void delete(Long id) throws EntityNotFoundException {
        conditionsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Conditions not found"));
        conditionsRepository.deleteById(id);
    }

    @Override
    public GenricTemplateEntry getById(Long id) throws EntityNotFoundException {
        GenericTemplate conditions = conditionsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Conditions not found"));
        return convertToEntry(conditions);
    }
    
    @Override
    public List<GenricTemplateEntry> getAllConditionsByStudioId(Long studioId, String templateType, Integer page, Integer size) throws Exception {
        Pageable pageable = size == -1 ? Pageable.unpaged() : PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "lastModifiedOn"));
        Page<GenericTemplate> conditionsPage = conditionsRepository.findByStudioIdAndFilters(studioId, templateType, pageable);
        return conditionsPage.getContent().stream()
                .map(this::convertToEntry)
                .collect(Collectors.toList());
    }
    
    @Override
    public long countByFilters(Long studioId, String templateType ) throws Exception {
        return conditionsRepository.countByStudioIdAndFilters(studioId, templateType);
    }

    private GenericTemplate convertToEntity(GenricTemplateEntry entry, GenericTemplate existingConditions) throws Exception {
        GenericTemplate genericTemplate = (existingConditions != null) ? existingConditions : new GenericTemplate();

        if (Objects.nonNull(entry.getTemplateType())) {
            genericTemplate.setTemplateType(entry.getTemplateType());
        }
        if (Objects.nonNull(entry.getTemplateName())) {
            genericTemplate.setTemplateName(entry.getTemplateName());
        }
        if(Objects.nonNull(entry.getTemplateSubject())) {
            genericTemplate.setTemplateSubject(entry.getTemplateSubject());
        }
        if (Objects.nonNull(entry.getTemplateContent())) {
            genericTemplate.setTemplateContent(entry.getTemplateContent());
        }
        if (Objects.nonNull(entry.getStudioId())) {
            StudioEntry studioEntry = studioManager.getById(entry.getStudioId());
            genericTemplate.setStudio(ConvertToEntryUtil.convertToEntity(studioEntry, null));
        }

        return genericTemplate;
    }

    private GenricTemplateEntry convertToEntry(GenericTemplate genericTemplate) {
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
