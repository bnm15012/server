package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.SubscriptionPlanEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;

public interface SubscriptionPlanManager {

    SubscriptionPlanEntry createOrder(SubscriptionPlanEntry subscriptionPlanEntry);

    SubscriptionPlanEntry verifyPayment(String orderId, String paymentId, String signature) throws Exception;

    SubscriptionPlanEntry addSubscriptionPlan(SubscriptionPlanEntry subscriptionPlanEntry) throws EntityNotFoundException;

    SubscriptionPlanEntry updateSubscriptionPlan(Long subscriptionPlanId, SubscriptionPlanEntry subscriptionPlanEntry) throws EntityNotFoundException;

    void deleteSubscriptionPlan(Long subscriptionPlanId) throws EntityNotFoundException;

    SubscriptionPlanEntry getSubscriptionPlanByStudioId(Long studioId) throws EntityNotFoundException;

}
