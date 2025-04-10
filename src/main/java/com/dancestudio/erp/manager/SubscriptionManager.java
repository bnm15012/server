package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.SubscriptionEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;

public interface SubscriptionManager {

    SubscriptionEntry createOrder(SubscriptionEntry subscriptionEntry);

    SubscriptionEntry verifyPayment(String orderId, String paymentId, String signature) throws Exception;

    SubscriptionEntry addSubscription(SubscriptionEntry subscriptionEntry) throws EntityNotFoundException;

    SubscriptionEntry updateSubscription(Long subscriptionId, SubscriptionEntry subscriptionEntry) throws EntityNotFoundException;

    void deleteSubscription(Long subscriptionPlanId) throws EntityNotFoundException;

    SubscriptionEntry getSubscriptionPlanByStudioId(Long studioId) throws EntityNotFoundException;

}
