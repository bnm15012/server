package com.dancestudio.erp.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dancestudio.erp.converter.ActivityBatchConvertor;
import com.dancestudio.erp.entity.activity.ActivityBatch;
import com.dancestudio.erp.entry.activity.ActivityBatchEntry;
import com.dancestudio.erp.manager.ActivityBatchManager;
import com.dancestudio.erp.repository.Activity.ActivityBatchRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ActivityBatchManagerImpl implements ActivityBatchManager {

    private final ActivityBatchRepository activityBatchRepository;

    @Autowired
    public ActivityBatchManagerImpl(ActivityBatchRepository activityBatchRepository) {
        this.activityBatchRepository = activityBatchRepository;
    }

    @Override
    public ActivityBatchEntry add(ActivityBatchEntry entry) throws Exception {
        ActivityBatch entity = ActivityBatchConvertor.convertToEntity(entry, null);
        ActivityBatch saved = activityBatchRepository.save(entity);
        return ActivityBatchConvertor.convertToEntry(saved);
    }

    @Override
    public ActivityBatchEntry update(Long id, ActivityBatchEntry entry) throws Exception {
        ActivityBatch existing = activityBatchRepository.findById(id)
                .orElseThrow(() -> new Exception("Activity batch not found with id: " + id));

        ActivityBatch updatedEntity = ActivityBatchConvertor.convertToEntity(entry, existing);
        ActivityBatch saved = activityBatchRepository.save(updatedEntity);

        return ActivityBatchConvertor.convertToEntry(saved);
    }

    @Override
    public void delete(Long id) throws Exception {
        ActivityBatch existing = activityBatchRepository.findById(id)
                .orElseThrow(() -> new Exception("Activity batch not found with id: " + id));

        activityBatchRepository.delete(existing);
    }

    @Override
    public ActivityBatchEntry getById(Long id) throws Exception {
        ActivityBatch activityBatch = activityBatchRepository.findById(id)
                .orElseThrow(() -> new Exception("Activity batch not found with id: " + id));

        return ActivityBatchConvertor.convertToEntry(activityBatch);
    }
}
