package com.dancestudio.erp.manager;

import com.dancestudio.erp.entry.SubscriptionEntry;

public interface SubscriptionManager extends BaseManager<SubscriptionEntry, Long> {

    SubscriptionEntry createOrder(SubscriptionEntry subscriptionEntry);

    SubscriptionEntry verifyPayment(String orderId, String paymentId, String signature) throws Exception;

    SubscriptionEntry getSubscriptionPlanByStudioId(Long studioId) throws Exception;

}
