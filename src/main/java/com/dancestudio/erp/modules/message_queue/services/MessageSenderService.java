package com.dancestudio.erp.modules.message_queue.services;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.dancestudio.erp.modules.message_queue.events.EmailQueuedEvent;
import com.dancestudio.erp.modules.message_queue.events.WhatsAppMessageQueuedEvent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageSenderService {

    private final EmailService emailService;

    private final WhatsAppService whatsAppService;

    private final AtomicBoolean running = new AtomicBoolean(false);
    private final AtomicBoolean runningEmail = new AtomicBoolean(false);

    MessageSenderService(WhatsAppService whatsAppService, EmailService emailService) {
        this.whatsAppService = whatsAppService;
        this.emailService = emailService;
    }

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @EventListener
    public void handleWhatsAppMessageQueuedEvent(WhatsAppMessageQueuedEvent event) {
        log.info("event triggered to send whatsapp message");

        if (!running.compareAndSet(false, true)) {
            log.info("one event already rnning so safely avoid this trigger");
            return;
        }
        try {
            running.set(true);
            executorService.submit(() -> {
                whatsAppService.processBulkWhatsAppMessages();
                running.set(false);
            });
            log.info("triggering worker to send message thread deleted");
        } catch (Exception e) {
            running.set(false);
        } finally {
            log.info("stop event");
        }
    }

    @EventListener
    public void handleEmailQueuedEvent(EmailQueuedEvent event) {
        log.info("event triggered to send email");

        if (!runningEmail.compareAndSet(false, true)) {
            log.info("one event already rnning so safely avoid this trigger");
            return;
        }
        try {
            runningEmail.set(true);
            executorService.submit(() -> {
                emailService.processBulkEmails();
                runningEmail.set(false);
            });
            log.info("triggering worker to send email thread deleted");
        } catch (Exception e) {
            runningEmail.set(false);
        } finally {
            log.info("stop event");
        }
    }
}
