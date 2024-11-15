package com.dancestudio.erp.manager.impl;


import com.dancestudio.erp.entity.SubscriptionPlan;
import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.entry.SubscriptionPlanEntry;
import com.dancestudio.erp.enums.SubscriptionStatus;
import com.dancestudio.erp.enums.SubscriptionType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.StudioManager;
import com.dancestudio.erp.manager.SubscriptionPlanManager;
import com.dancestudio.erp.repository.SubscriptionPlanRepository;
import com.dancestudio.erp.util.ConvertToEntryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class SubscriptionPlanManagerImpl implements SubscriptionPlanManager {
    private final SubscriptionPlanRepository subscriptionPlanRepository;

    @Autowired
    private StudioManager studioManager;

    @Autowired
    public SubscriptionPlanManagerImpl(SubscriptionPlanRepository subscriptionPlanRepository) {
        this.subscriptionPlanRepository = subscriptionPlanRepository;
    }

    @Override
    public SubscriptionPlanEntry addSubscriptionPlan(SubscriptionPlanEntry subscriptionPlanEntry) throws EntityNotFoundException {
        SubscriptionPlan subscriptionPlan = convertToEntity(subscriptionPlanEntry, null);
        return convertToEntry(subscriptionPlanRepository.save(subscriptionPlan));
    }

    @Override
    public SubscriptionPlanEntry updateSubscriptionPlan(Long subscriptionPlanId, SubscriptionPlanEntry subscriptionPlanEntry) throws EntityNotFoundException {
        SubscriptionPlan existingSubscriptionPlan = subscriptionPlanRepository.findById(subscriptionPlanId)
                .orElseThrow(() -> new EntityNotFoundException("SubscriptionPlan not found"));

        SubscriptionPlan updatedSubscriptionPlan = convertToEntity(subscriptionPlanEntry, existingSubscriptionPlan);
        return convertToEntry(subscriptionPlanRepository.save(updatedSubscriptionPlan));
    }

    @Override
    public void deleteSubscriptionPlan(Long subscriptionPlanId) throws EntityNotFoundException {
        subscriptionPlanRepository.findById(subscriptionPlanId)
                .orElseThrow(() -> new EntityNotFoundException("SubscriptionPlan not found"));

        subscriptionPlanRepository.deleteById(subscriptionPlanId);
    }

    @Override
    public SubscriptionPlanEntry getSubscriptionPlanById(Long subscriptionPlanId) throws EntityNotFoundException {
        SubscriptionPlan subscriptionPlan = subscriptionPlanRepository.findById(subscriptionPlanId)
                .orElseThrow(() -> new EntityNotFoundException("SubscriptionPlan not found"));

        return convertToEntry(subscriptionPlan);
    }

    private SubscriptionPlanEntry convertToEntry(SubscriptionPlan subscriptionPlan) throws EntityNotFoundException {

        SubscriptionPlanEntry subscriptionPlanEntry = new SubscriptionPlanEntry();
        subscriptionPlanEntry.setPlanId(subscriptionPlan.getId());

        StudioEntry entry = studioManager.getStudioById(subscriptionPlan.getStudio().getId());
        subscriptionPlanEntry.setStudioId(entry.getStudioId());

        subscriptionPlanEntry.setSubscriptionPlan(SubscriptionType.valueOf(subscriptionPlan.getSubscriptionPlan()));
        subscriptionPlanEntry.setStartDate(subscriptionPlan.getStartDate());
        subscriptionPlanEntry.setEndDate(subscriptionPlan.getEndDate());
        subscriptionPlanEntry.setStatus(SubscriptionStatus.valueOf(subscriptionPlan.getStatus()));
        subscriptionPlanEntry.setPrice(subscriptionPlan.getPrice().doubleValue());
        subscriptionPlanEntry.setRenewalDate(subscriptionPlan.getRenewalDate());

        return subscriptionPlanEntry;
    }

    private SubscriptionPlan convertToEntity(SubscriptionPlanEntry subscriptionPlanEntry, SubscriptionPlan existingSubscriptionPlan) throws EntityNotFoundException {
        SubscriptionPlan subscriptionPlan = (existingSubscriptionPlan != null) ? existingSubscriptionPlan : new SubscriptionPlan();

        if (Objects.nonNull(subscriptionPlanEntry.getPlanId())) {
            subscriptionPlan.setId(subscriptionPlanEntry.getPlanId());
        }
        if (Objects.nonNull(subscriptionPlanEntry.getStudioId())) {
            StudioEntry entry = studioManager.getStudioById(subscriptionPlanEntry.getStudioId());
            subscriptionPlan.setStudio(ConvertToEntryUtil.convertToEntity(entry, null));
        }
        if (Objects.nonNull(subscriptionPlanEntry.getSubscriptionPlan())) {
            subscriptionPlan.setSubscriptionPlan(String.valueOf(subscriptionPlanEntry.getSubscriptionPlan()));
        }
        if (Objects.nonNull(subscriptionPlanEntry.getStartDate())) {
            subscriptionPlan.setStartDate(subscriptionPlanEntry.getStartDate());
        }
        if (Objects.nonNull(subscriptionPlanEntry.getEndDate())) {
            subscriptionPlan.setEndDate(subscriptionPlanEntry.getEndDate());
        }
        if (Objects.nonNull(subscriptionPlanEntry.getStatus())) {
            subscriptionPlan.setStatus(String.valueOf(subscriptionPlanEntry.getStatus()));
        }
        if (Objects.nonNull(subscriptionPlanEntry.getPrice())) {
            subscriptionPlan.setPrice(BigDecimal.valueOf(subscriptionPlanEntry.getPrice()));
        }
        if (Objects.nonNull(subscriptionPlanEntry.getRenewalDate())) {
            subscriptionPlan.setRenewalDate(subscriptionPlanEntry.getRenewalDate());
        }

        return subscriptionPlan;
    }
}
