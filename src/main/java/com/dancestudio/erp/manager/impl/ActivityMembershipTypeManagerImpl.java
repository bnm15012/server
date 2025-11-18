package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.converter.ActivityMembershipTypeConverter;
import com.dancestudio.erp.entry.activity.ActivityMembershipTypeEntry;
import com.dancestudio.erp.entity.activity.ActivityMembershipType;
import com.dancestudio.erp.manager.ActivityMembershipTypeManager;
import com.dancestudio.erp.repository.Activity.ActivityMembershipTypeRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ActivityMembershipTypeManagerImpl implements ActivityMembershipTypeManager {

    private final ActivityMembershipTypeRepository repository;

    public ActivityMembershipTypeManagerImpl(ActivityMembershipTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public ActivityMembershipTypeEntry add(ActivityMembershipTypeEntry entry) throws Exception {
        ActivityMembershipType entity = ActivityMembershipTypeConverter.toEntity(entry, null);
        ActivityMembershipType saved = repository.save(entity);
        return ActivityMembershipTypeConverter.toEntry(saved);
    }

    @Override
    public ActivityMembershipTypeEntry update(Long id, ActivityMembershipTypeEntry entry) throws Exception {
        Optional<ActivityMembershipType> existing = repository.findById(id);
        if (existing.isEmpty()) {
            throw new Exception("ActivityMembershipType with id " + id + " not found");
        }

        ActivityMembershipType toUpdate = existing.get();
        ActivityMembershipTypeConverter.toEntity(entry, toUpdate);
        ActivityMembershipType saved = repository.save(toUpdate);
        return ActivityMembershipTypeConverter.toEntry(saved);
    }

    @Override
    public void delete(Long id) throws Exception {
        if (!repository.existsById(id)) {
            throw new Exception("ActivityMembershipType with id " + id + " not found");
        }
        repository.deleteById(id);
    }

    @Override
    public ActivityMembershipTypeEntry getById(Long id) throws Exception {
        ActivityMembershipType entity = repository.findById(id)
                .orElseThrow(() -> new Exception("ActivityMembershipType with id " + id + " not found"));
        return ActivityMembershipTypeConverter.toEntry(entity);
    }

    @Override
    public Page<ActivityMembershipType> getAllByStudioId(Long studioId, Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        return repository.findByStudioId(studioId, pageable);
    }
}
