package com.dancestudio.erp.modules.membershipPackage;

import com.dancestudio.erp.manager.impl.StudioManagerImpl;
import com.dancestudio.erp.modules.studio.StudioConvertor;
import jakarta.annotation.PostConstruct;

import java.util.Objects;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


@Component
public class MembershipPackagesConverter {

    private static ApplicationContext applicationContext;

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        applicationContext = context;
    }

    public static MembershipPackagesEntry toEntry(MembershipPackages entity) {
        if (entity == null)
            return null;

        MembershipPackagesEntry entry = new MembershipPackagesEntry();
        entry.setId(entity.getId());
        entry.setMembershipPackage(entity.getMembershipType());
        entry.setDays(entity.getDays());
        if (Objects.nonNull(entity.getStudio().getId())) {
            entry.setStudioId(entity.getStudio().getId());
        }
        return entry;
    }

    public static MembershipPackages toEntity(MembershipPackagesEntry entry,
            MembershipPackages existingEntity) throws BeansException, Exception {
        if (entry == null)
            return null;

        MembershipPackages entity = existingEntity != null ? existingEntity : new MembershipPackages();

        if (Objects.nonNull(entry.getId())) {
            entity.setId(entry.getId());
        }
        if (Objects.nonNull(entry.getDays())) {
            entity.setDays(entry.getDays());
        }
        if (Objects.nonNull(entry.getStudioId())) {
            entity.setStudio(StudioConvertor.convertToEntity(
                    applicationContext.getBean(StudioManagerImpl.class).getById(entry.getStudioId()), null));
        }
        if (Objects.nonNull(entry.getMembershipPackage())) {
            entity.setMembershipType(entry.getMembershipPackage());
        }
        return entity;
    }
}
