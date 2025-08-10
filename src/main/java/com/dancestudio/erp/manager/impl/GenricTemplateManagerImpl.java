package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.GenericTemplate;
import com.dancestudio.erp.entry.BranchEntry;
import com.dancestudio.erp.entry.GenricTemplateEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.BranchManager;
import com.dancestudio.erp.manager.GenricTemplateManager;
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
    private BranchManager branchManager;

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
    public List<GenricTemplateEntry> getAllConditionsByBranchId(Long branchId, String templateType, Integer page, Integer size) throws Exception {
        Pageable pageable = size == -1 ? Pageable.unpaged() : PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "lastModifiedOn"));
        Page<GenericTemplate> conditionsPage = conditionsRepository.findByBranchIdAndFilters(branchId, templateType, pageable);
        return conditionsPage.getContent().stream()
                .map(this::convertToEntry)
                .collect(Collectors.toList());
    }
    
    @Override
    public long countByFilters(Long branchId, String templateType ) throws Exception {
        return conditionsRepository.countByBranchIdAndFilters(branchId, templateType);
    }

    private GenericTemplate convertToEntity(GenricTemplateEntry entry, GenericTemplate existingConditions) throws Exception {
        GenericTemplate conditions = (existingConditions != null) ? existingConditions : new GenericTemplate();

        if (Objects.nonNull(entry.getTemplateType())) {
            conditions.setTemplateType(entry.getTemplateType());
        }
        if (Objects.nonNull(entry.getTemplateName())) {
            conditions.setTemplateName(entry.getTemplateName());
        }
        if(Objects.nonNull(entry.getTemplateSubject())) {
            conditions.setTemplateSubject(entry.getTemplateSubject());
        }
        if (Objects.nonNull(entry.getTemplateContent())) {
            conditions.setTemplateContent(entry.getTemplateContent());
        }
        if (Objects.nonNull(entry.getBranchId())) {
            BranchEntry branchEntry = branchManager.getById(entry.getBranchId());
            conditions.setBranch(ConvertToEntryUtil.convertToEntity(branchEntry, null));
        }

        return conditions;
    }

    private GenricTemplateEntry convertToEntry(GenericTemplate conditions) {
        GenricTemplateEntry entry = new GenricTemplateEntry();
        entry.setId(conditions.getId());
        entry.setTemplateType(conditions.getTemplateType());
        entry.setTemplateName(conditions.getTemplateName());
        entry.setTemplateSubject(conditions.getTemplateSubject());
        entry.setTemplateContent(conditions.getTemplateContent());
        if (conditions.getBranch() != null) {
            entry.setBranchId(conditions.getBranch().getId());
        }
        return entry;
    }
}
