package com.dancestudio.erp.modules.activity.manager;

import org.springframework.beans.BeansException;
import org.springframework.stereotype.Service;

import com.dancestudio.erp.base.BaseManager;
import com.dancestudio.erp.entity.activity.ActivityBatch;
import com.dancestudio.erp.entry.activity.ActivityBatchEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.modules.activity.convertor.ActivityBatchConvertor;
import com.dancestudio.erp.modules.activity.repository.ActivityBatchRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ActivityBatchManager extends BaseManager<ActivityBatch, Long, ActivityBatchEntry> {


    public ActivityBatchManager(ActivityBatchRepository activityBatchRepository) {
        super(activityBatchRepository, "Activity Batch");
    }

    @Override
    protected ActivityBatch toEntity(ActivityBatchEntry entry, ActivityBatch existing)
            throws EntityNotFoundException, BeansException, Exception {
        return ActivityBatchConvertor.convertToEntity(entry, existing);
    }

    @Override
    protected ActivityBatchEntry toEntry(ActivityBatch entity, String[] fields) throws EntityNotFoundException {
        return ActivityBatchConvertor.convertToEntry(entity);
    }

}
