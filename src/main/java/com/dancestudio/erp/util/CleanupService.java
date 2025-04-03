package com.dancestudio.erp.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.dancestudio.erp.enums.SubscriptionStatus;
import com.dancestudio.erp.repository.SubscriptionPlanRepository;

@Service
public class CleanupService {

    private static final Logger logger = LoggerFactory.getLogger(CleanupService.class);
    private final SubscriptionPlanRepository subscriptionPlanRepository;

    @Value("${cleanup.cron}")
    private String cronExpression;

    public CleanupService(SubscriptionPlanRepository subscriptionPlanRepository) {
        this.subscriptionPlanRepository = subscriptionPlanRepository;
    }

    @Scheduled(cron = "${cleanup.cron}")
    public void removeUnverifiedSubscriptions() {
        logger.info("Starting cleanup for unverified subscriptions...");
        LocalDate startLocalDate = LocalDate.now();
        startLocalDate.plusDays(1);
        Date cutoffDate = Date.from(startLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        int deletedCount = subscriptionPlanRepository.deleteByStatusAndCreatedAtBefore(
                SubscriptionStatus.CREATED.toString(), cutoffDate);
        logger.info("Cleanup completed. Deleted {} unverified subscriptions.", deletedCount);
    }
}
