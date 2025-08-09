package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.Conditions;
import com.dancestudio.erp.entry.BranchEntry;
import com.dancestudio.erp.entry.ConditionsEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.BranchManager;
import com.dancestudio.erp.manager.ConditionsManager;
import com.dancestudio.erp.repository.ConditionsRepository;
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
public class ConditionsManagerImpl implements ConditionsManager {

    private final ConditionsRepository conditionsRepository;

    @Autowired
    private BranchManager branchManager;

    @Autowired
    public ConditionsManagerImpl(ConditionsRepository conditionsRepository) {
        this.conditionsRepository = conditionsRepository;
    }

    @Override
    @Transactional
    public ConditionsEntry add(ConditionsEntry conditionsEntry) throws Exception {
        Conditions conditions = convertToEntity(conditionsEntry, null);
        return convertToEntry(conditionsRepository.save(conditions));
    }

    @Override
    @Transactional
    public ConditionsEntry update(Long id, ConditionsEntry conditionsEntry) throws Exception {
        Conditions existingConditions = conditionsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Conditions not found"));

        Conditions updatedConditions = convertToEntity(conditionsEntry, existingConditions);
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
    public ConditionsEntry getById(Long id) throws EntityNotFoundException {
        Conditions conditions = conditionsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Conditions not found"));
        return convertToEntry(conditions);
    }
    
    @Override
    public List<ConditionsEntry> getAllConditionsByBranchId(Long branchId, String entityType, String activityType, Integer page, Integer size) throws Exception {
        Pageable pageable = size == -1 ? Pageable.unpaged() : PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "lastModifiedOn"));
        Page<Conditions> conditionsPage = conditionsRepository.findByBranchIdAndFilters(branchId, entityType, activityType, pageable);
        return conditionsPage.getContent().stream()
                .map(this::convertToEntry)
                .collect(Collectors.toList());
    }
    
    @Override
    public long countByFilters(Long branchId, String entityType, String activityType) throws Exception {
        return conditionsRepository.countByBranchIdAndFilters(branchId, entityType, activityType);
    }

    private Conditions convertToEntity(ConditionsEntry entry, Conditions existingConditions) throws Exception {
        Conditions conditions = (existingConditions != null) ? existingConditions : new Conditions();

        if (Objects.nonNull(entry.getEntityType())) {
            conditions.setEntityType(entry.getEntityType());
        }
        if (Objects.nonNull(entry.getDescription())) {
            conditions.setDescription(entry.getDescription());
        }
        if(Objects.nonNull(entry.getTemplateName())) {
            conditions.setTemplateName(entry.getTemplateName());
        }
        if (Objects.nonNull(entry.getActivityType())) {
            conditions.setActivityType(entry.getActivityType());
        }
        if (Objects.nonNull(entry.getBranchId())) {
            BranchEntry branchEntry = branchManager.getById(entry.getBranchId());
            conditions.setBranch(ConvertToEntryUtil.convertToEntity(branchEntry, null));
        }

        return conditions;
    }

    private ConditionsEntry convertToEntry(Conditions conditions) {
        ConditionsEntry entry = new ConditionsEntry();
        entry.setId(conditions.getId());
        entry.setEntityType(conditions.getEntityType());
        entry.setActivityType(conditions.getActivityType());
        entry.setTemplateName(conditions.getTemplateName());
        entry.setDescription(conditions.getDescription());
        if (conditions.getBranch() != null) {
            entry.setBranchId(conditions.getBranch().getId());
        }
        return entry;
    }
}
