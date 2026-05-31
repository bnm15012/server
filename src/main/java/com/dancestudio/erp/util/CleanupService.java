package com.dancestudio.erp.util;

import com.dancestudio.erp.modules.invoiceToken.InvoiceTokenRepository;
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
    private final InvoiceTokenRepository studentInvoiceTokenRepository;

    public CleanupService(SubscriptionRepository subscriptionRepository,
            InvoiceTokenRepository studentInvoiceTokenRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.studentInvoiceTokenRepository = studentInvoiceTokenRepository;
    }

    // @Scheduled(cron = "${cleanup.cron}", zone = "Asia/Kolkata")
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

    @Scheduled(cron = "${cleanup.cron}", zone = "Asia/Kolkata")
    public void cleanExpiredInvoiceTokens() {
        log.info("Starting cleanup for expired invoice tokens...");
        try {
            int deletedCount = studentInvoiceTokenRepository.deleteByExpiresAtBefore(new Date());
            log.info("Cleanup completed. Removed {} expired student invoice tokens.", deletedCount);
        } catch (Exception e) {
            log.error("Error occurred during cleanup of expired student invoice tokens: {}", e.getMessage(), e);
        }
    }
}
