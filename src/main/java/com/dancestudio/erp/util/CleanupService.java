package com.dancestudio.erp.util;

import com.dancestudio.erp.enums.SubscriptionStatus;
import com.dancestudio.erp.repository.SubscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class CleanupService {

    private static final Logger logger = LoggerFactory.getLogger(CleanupService.class);
    private final SubscriptionRepository subscriptionRepository;

    public CleanupService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Scheduled(cron = "${cleanup.cron}")
    public void markUnverifiedSubscriptionsExpired() {
        logger.info("Starting cleanup for unverified subscriptions...");
        LocalDate startLocalDate = LocalDate.now().plusDays(1);
        Date cutoffDate = Date.from(startLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        try {
            int updatedCount = subscriptionRepository.updateStatusByCreatedAtBefore(SubscriptionStatus.EXPIRED.toString(), cutoffDate);
            logger.info("Cleanup completed. Updated {} unverified subscriptions to EXPIRED.", updatedCount);
        } catch (Exception e) {
            logger.error("Error occurred during cleanup of unverified subscriptions: {}", e.getMessage(), e);
        }
    }
}
