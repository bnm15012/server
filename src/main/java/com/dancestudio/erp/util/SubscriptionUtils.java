package com.dancestudio.erp.util;

import com.dancestudio.erp.entity.Subscription;
import com.dancestudio.erp.entry.SubscriptionEntry;
import com.dancestudio.erp.enums.SubscriptionType;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class SubscriptionUtils {
    public static void setSubscriptionDates(Subscription subscription, SubscriptionEntry subscriptionEntry, SubscriptionType subscriptionType) {

        LocalDate startLocalDate, endLocalDate;
        if (Objects.nonNull(subscriptionEntry.getStartDate())) {
            startLocalDate = subscriptionEntry.getStartDate()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        } else {
            startLocalDate = LocalDate.now();
        }

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
            case AMC:
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

    public static Date calculateEndDate(Date startDate, SubscriptionType subscriptionType) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        switch (subscriptionType) {
            case MONTHLY:
                calendar.add(Calendar.MONTH, 1);
                break;
            case QUARTERLY:
                calendar.add(Calendar.MONTH, 3);
                break;
            case HALF_YEARLY:
                calendar.add(Calendar.MONTH, 6);
                break;
            case YEARLY:
                calendar.add(Calendar.YEAR, 1);
                break;
            case AMC:
                calendar.add(Calendar.YEAR, 1);
                break;
            default:
                throw new IllegalArgumentException("Unsupported subscription type: " + subscriptionType);
        }

        return calendar.getTime();
    }
}
