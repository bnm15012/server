package com.dancestudio.erp.modules.activity.manager;

import com.dancestudio.erp.base.BaseManager;
import com.dancestudio.erp.entity.activity.Activity;
import com.dancestudio.erp.entry.activity.ActivityEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.modules.activity.convertor.ActivityConverter;
import com.dancestudio.erp.modules.activity.repository.ActivityBatchRepository;
import com.dancestudio.erp.modules.activity.repository.ActivityRepository;

import org.springframework.beans.BeansException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityManager extends BaseManager<Activity, Long, ActivityEntry> {

    private final ActivityRepository activityRepository;

    public ActivityManager(ActivityRepository activityRepository,
            ActivityBatchRepository activityMembershipRepository) {
        super(activityRepository, "Activity");
        this.activityRepository = activityRepository;
    }

    public List<ActivityEntry> getAllActivities(Long branchId) throws Exception {
        List<Activity> entries = activityRepository.findByBranchId(branchId);

        List<ActivityEntry> activityEntries = new ArrayList<>();
        for (Activity entry : entries) {
            ActivityEntry activityEntry = ActivityConverter.convertToEntry(entry);
            activityEntries.add(activityEntry);
        }

        return activityEntries;
    }

    @Override
    protected Activity toEntity(ActivityEntry entry, Activity existing)
            throws EntityNotFoundException, BeansException, Exception {
        return ActivityConverter.convertToEntity(entry, existing);
    }

    @Override
    protected ActivityEntry toEntry(Activity entity, String[] fields) throws EntityNotFoundException {
        return ActivityConverter.convertToEntry(entity);
    }
}
