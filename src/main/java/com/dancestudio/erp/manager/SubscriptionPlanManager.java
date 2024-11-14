package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.SubscriptionPlanEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;

public interface SubscriptionPlanManager {

    SubscriptionPlanEntry addSubscriptionPlan(SubscriptionPlanEntry subscriptionPlanEntry) throws EntityNotFoundException;

    SubscriptionPlanEntry updateSubscriptionPlan(Long subscriptionPlanId, SubscriptionPlanEntry subscriptionPlanEntry) throws EntityNotFoundException;

    void deleteSubscriptionPlan(Long subscriptionPlanId) throws EntityNotFoundException;

    SubscriptionPlanEntry getSubscriptionPlanById(Long subscriptionPlanId) throws EntityNotFoundException;

}
