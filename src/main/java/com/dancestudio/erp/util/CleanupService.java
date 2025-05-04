package com.dancestudio.erp.util;

import com.dancestudio.erp.repository.SubscriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
@Slf4j
public class CleanupService {

    private final SubscriptionRepository subscriptionRepository;

    public CleanupService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Scheduled(cron = "${cleanup.cron}", zone = "Asia/Kolkata")
    public void markUnverifiedSubscriptionsExpired() {
        log.info("Starting cleanup for unverified subscriptions...");
        LocalDate startLocalDate = LocalDate.now().plusDays(1);
        Date cutoffDate = Date.from(startLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        try {
            int updatedCount = subscriptionRepository.deleteByCreatedAtBeforeAndStatusCreated(cutoffDate);
            log.info("Cleanup completed. Removed {} unverified subscriptions.", updatedCount);
        } catch (Exception e) {
            log.error("Error occurred during cleanup of unverified subscriptions: {}", e.getMessage(), e);
        }
    }
}
