package com.dancestudio.erp.util;

import com.dancestudio.erp.entity.Studio;
import com.dancestudio.erp.entity.User;
import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.entry.UserEntry;
import com.dancestudio.erp.enums.UserType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.impl.StudioManagerImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ConvertToEntryUtil {

    private static ApplicationContext applicationContext;

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        applicationContext = context;
    }

    public static UserEntry convertToEntry(User user) throws EntityNotFoundException {

        UserEntry userEntry = new UserEntry();
        userEntry.setUserId(user.getId());
        userEntry.setUserName(user.getName());
        userEntry.setEmail(user.getEmail());
        userEntry.setPhone(user.getPhone());
        userEntry.setEnabled(user.isEnabled());
        userEntry.setRole(UserType.valueOf(user.getRole()));

        if (Objects.nonNull(user.getStudio().getId())) {
            StudioManagerImpl studioManagerImpl = applicationContext.getBean(StudioManagerImpl.class);
            StudioEntry studioEntry = studioManagerImpl.getStudioById(user.getStudio().getId());
            userEntry.setStudioEntry(studioEntry);
        }
        return userEntry;
    }

    public static User convertToEntity(UserEntry userEntry, User existingUser) throws Exception {
        User user = (existingUser != null) ? existingUser : new User();
        user.setId(null);

        if (Objects.nonNull(userEntry.getUserId())) {
            user.setId(userEntry.getUserId());
        }
        if (Objects.nonNull(userEntry.getUserName())) {
            user.setName(userEntry.getUserName());
        }
        if (Objects.nonNull(userEntry.getPassword())) {
            user.setPassword(userEntry.getPassword());
        }
        if (Objects.nonNull(userEntry.getRole())) {
            user.setRole(String.valueOf(userEntry.getRole()));
        }
        if (Objects.nonNull(userEntry.getPhone())) {
            user.setPhone(userEntry.getPhone());
        }
        if (Objects.nonNull(userEntry.getEmail())) {
            user.setEmail(userEntry.getEmail());
        }
        if (Objects.nonNull(userEntry.getEnabled())) {
            user.setEnabled(userEntry.getEnabled());
        }


        if (Objects.nonNull(userEntry.getStudioEntry()) && Objects.nonNull(userEntry.getStudioEntry().getStudioId())) {
            Long studioId = userEntry.getStudioEntry().getStudioId();

            StudioManagerImpl studioManagerImpl = applicationContext.getBean(StudioManagerImpl.class);
            StudioEntry studioEntry = studioManagerImpl.getStudioById(studioId);
            user.setStudio(convertToEntity(studioEntry, null));
        }

        return user;
    }

    public static StudioEntry convertToEntry(Studio studio) {

        StudioEntry studioEntry = new StudioEntry();
        studioEntry.setStudioId(studio.getId());
        studioEntry.setStudioName(studio.getName());
        studioEntry.setLocation(studio.getLocation());
        studioEntry.setContactDetails(studio.getContactDetails());

        return studioEntry;
    }

    public static Studio convertToEntity(StudioEntry studioEntry, Studio existingStudio) {
        Studio studio = (existingStudio != null) ? existingStudio : new Studio();

        if (Objects.nonNull(studioEntry.getStudioId())) {
            studio.setId(studioEntry.getStudioId());
        }
        if (Objects.nonNull(studioEntry.getStudioName())) {
            studio.setName(studioEntry.getStudioName());
        }
        if (Objects.nonNull(studioEntry.getLogo())) {
            studio.setLogo(studioEntry.getLogo());
        }
        if (Objects.nonNull(studioEntry.getLocation())) {
            studio.setLocation(studioEntry.getLocation());
        }
        if (Objects.nonNull(studioEntry.getContactDetails())) {
            studio.setContactDetails(studioEntry.getContactDetails());
        }
        if (Objects.nonNull(studioEntry.getEnabled())) {
            studio.setEnabled(studioEntry.getEnabled());
        }

        return studio;
    }
}
