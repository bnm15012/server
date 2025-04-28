package com.dancestudio.erp.entry;

import com.dancestudio.erp.enums.SubscriptionType;
import com.dancestudio.erp.enums.SubscriptionStatus;
import lombok.Data;

import java.util.Date;

@Data
public class SubscriptionEntry {

    private Long planId;
    private Long studioId;
    private Long branchId;
    private SubscriptionType subscriptionPlan; // e.g., "Monthly", "Yearly", "Half-Yearly"
    private Date startDate;
    private Date endDate;
    private SubscriptionStatus status; // e.g., "ACTIVE", "EXPIRED", "CANCELLED"
    private Double price;
    private Date renewalDate;
    private String orderId;
    private String paymentId;
    private String signature;
    private String message;
}
