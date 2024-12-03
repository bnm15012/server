package com.dancestudio.erp.util;

import com.dancestudio.erp.entity.Activity;
import com.dancestudio.erp.entity.BankAccount;
import com.dancestudio.erp.entity.Studio;
import com.dancestudio.erp.entity.User;
import com.dancestudio.erp.entry.*;
import com.dancestudio.erp.enums.ActivityType;
import com.dancestudio.erp.enums.UserType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.impl.StudioManagerImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ConvertToEntryUtil {

    private static ApplicationContext applicationContext;
    private final static ObjectMapper objectMapper = new ObjectMapper();

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
        userEntry.setImageUrl(user.getProfileImage());
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
        if (Objects.nonNull(userEntry.getImageUrl())) {
            user.setProfileImage(userEntry.getImageUrl());
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

        return studio;
    }

    public static BankAccountEntry convertToEntry(BankAccount bankAccount) {

        BankAccountEntry bankAccountEntry = new BankAccountEntry();
        bankAccountEntry.setBankAccountId(bankAccount.getId());
        bankAccountEntry.setBankName(bankAccount.getBankName());
        bankAccountEntry.setAccountNumber(bankAccount.getAccountNumber());
        bankAccountEntry.setBranchName(bankAccount.getBranchName());
        bankAccountEntry.setIfscCode(bankAccount.getIfscCode());
        bankAccountEntry.setUpiId(bankAccount.getUpiId());
        return bankAccountEntry;
    }

    public static BankAccount convertToEntity(BankAccountEntry bankAccountEntry, BankAccount existingBankAccount) throws EntityNotFoundException {
        BankAccount bankAccount = (existingBankAccount != null) ? existingBankAccount : new BankAccount();

        if (Objects.nonNull(bankAccountEntry.getBankAccountId())) {
            bankAccount.setId(bankAccountEntry.getBankAccountId());
        }
        if (Objects.nonNull(bankAccountEntry.getAccountNumber())) {
            bankAccount.setAccountNumber(bankAccountEntry.getAccountNumber());
        }
        if (Objects.nonNull(bankAccountEntry.getBankName())) {
            bankAccount.setBankName(bankAccountEntry.getBankName());
        }
        if (Objects.nonNull(bankAccountEntry.getBranchName())) {
            bankAccount.setBranchName(bankAccountEntry.getBranchName());
        }
        if (Objects.nonNull(bankAccountEntry.getIfscCode())) {
            bankAccount.setIfscCode(bankAccountEntry.getIfscCode());
        }
        if (Objects.nonNull(bankAccountEntry.getUpiId())) {
            bankAccount.setUpiId(bankAccountEntry.getUpiId());
        }
        return bankAccount;
    }

    public static ActivityEntry convertToEntry(Activity activity) throws EntityNotFoundException {

        ActivityEntry activityEntry = new ActivityEntry();
        activityEntry.setActivityId(activity.getId());
        activityEntry.setActivityType(ActivityType.valueOf(activity.getActivityType()));
        activityEntry.setDescription(activity.getDescription());

        StudioManagerImpl studioManagerImpl = applicationContext.getBean(StudioManagerImpl.class);
        StudioEntry studioEntry = studioManagerImpl.getStudioById(activity.getStudio().getId());
        activityEntry.setStudioId(studioEntry.getStudioId());

        if (activity.getMembershipPlans() != null) {
            try {
                List<MembershipPlanEntry> membershipPlans = objectMapper.readValue(
                        activity.getMembershipPlans(),
                        new TypeReference<>() {}
                );
                MembershipPlanRequest request = new MembershipPlanRequest();
                request.setMembershipPlanEntryList(membershipPlans);
                activityEntry.setMembershipPlanRequest(request);
            } catch (Exception e) {
                throw new RuntimeException("Error parsing membership plans JSON", e);
            }
        }

        return activityEntry;
    }

    public static Activity convertToEntity(ActivityEntry activityEntry, Activity existingActivity) throws EntityNotFoundException {
        Activity activity = (existingActivity != null) ? existingActivity : new Activity();

        if (Objects.nonNull(activityEntry.getActivityId())) {
            activity.setId(activityEntry.getActivityId());
        }
        if (Objects.nonNull(activityEntry.getActivityType())) {
            activity.setActivityType(activityEntry.getActivityType().name());
        }
        if (Objects.nonNull(activityEntry.getDescription())) {
            activity.setDescription(activityEntry.getDescription());
        }

        if (Objects.nonNull(activityEntry.getStudioId())) {
            StudioManagerImpl studioManagerImpl = applicationContext.getBean(StudioManagerImpl.class);
            StudioEntry studioEntry = studioManagerImpl.getStudioById(activityEntry.getStudioId());

            activity.setStudio(ConvertToEntryUtil.convertToEntity(studioEntry, null));
        }

        if (Objects.nonNull(activityEntry.getMembershipPlanRequest())) {
            List<MembershipPlanEntry> membershipPlans = activityEntry.getMembershipPlanRequest().getMembershipPlanEntryList();
            try {
                String membershipPlansJson = objectMapper.writeValueAsString(membershipPlans);
                activity.setMembershipPlans(membershipPlansJson);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error converting membership plans to JSON", e);
            }
        }

        return activity;
    }
}
