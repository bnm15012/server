package com.dancestudio.erp.converter;

import com.dancestudio.erp.entry.activity.ActivityMembershipTypeEntry;
import com.dancestudio.erp.manager.impl.StudioManagerImpl;
import com.dancestudio.erp.util.ConvertToEntryUtil;

import jakarta.annotation.PostConstruct;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.dancestudio.erp.entity.activity.ActivityMembershipType;


@Component
public class ActivityMembershipTypeConverter {

    private static ApplicationContext applicationContext;

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        applicationContext = context;
    }

    public static ActivityMembershipTypeEntry toEntry(ActivityMembershipType entity) {
        if (entity == null)
            return null;

        ActivityMembershipTypeEntry entry = new ActivityMembershipTypeEntry();
        entry.setActivityMembershipTypeId(entity.getId());
        entry.setActivityMembershipType(entity.getMembershipType());
        if (Objects.nonNull(entity.getStudio().getId())) {
            entry.setStudioId(entity.getStudio().getId());
        }
        return entry;
    }

    public static ActivityMembershipType toEntity(ActivityMembershipTypeEntry entry,
            ActivityMembershipType existingEntity) throws Exception {
        if (entry == null)
            return null;

        ActivityMembershipType entity = existingEntity != null ? existingEntity : new ActivityMembershipType();

        if (Objects.nonNull(entry.getActivityMembershipTypeId())) {
            entity.setId(entry.getActivityMembershipTypeId());
        }
        if (Objects.nonNull(entry.getStudioId())) {
            entity.setStudio(ConvertToEntryUtil.convertToEntity(
                    applicationContext.getBean(StudioManagerImpl.class).getById(entry.getStudioId()), null));
        }
        if (Objects.nonNull(entry.getActivityMembershipType())) {
            entity.setMembershipType(entry.getActivityMembershipType());
        }
        return entity;
    }
}
