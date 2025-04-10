package com.dancestudio.erp.util;

import com.dancestudio.erp.entity.Subscription;
import com.dancestudio.erp.enums.SubscriptionType;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class SubscriptionUtils {
    public static void setSubscriptionDates(Subscription subscription, SubscriptionType subscriptionType) {
        LocalDate startLocalDate = LocalDate.now();  // Current date
        LocalDate endLocalDate;

        switch (subscriptionType) {
            case TRIAL:
                endLocalDate = startLocalDate.plusDays(7);
                break;
            case MONTHLY:
                endLocalDate = startLocalDate.plusMonths(1);
                break;
            case QUARTERLY:
                endLocalDate = startLocalDate.plusMonths(3);
                break;
            case HALF_YEARLY:
                endLocalDate = startLocalDate.plusMonths(6);
                break;
            case YEARLY:
                endLocalDate = startLocalDate.plusYears(1);
                break;
            default:
                throw new IllegalArgumentException("Invalid Subscription Type: " + subscriptionType);
        }

        // Convert LocalDate to java.util.Date
        Date startDate = Date.from(startLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(endLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Set the values in the subscription plan
        subscription.setStartDate(startDate);
        subscription.setEndDate(endDate);
    }
}
