package com.dancestudio.erp.message_queue.services;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.dancestudio.erp.entity.Branch;
import com.dancestudio.erp.entity.Member;
import com.dancestudio.erp.entity.Message;
import com.dancestudio.erp.entity.MessageRecipient;
import com.dancestudio.erp.entity.Studio;
import com.dancestudio.erp.enums.MessageStatus;
import com.dancestudio.erp.enums.NotificationType;
import com.dancestudio.erp.message_queue.MessageQueue;
import com.dancestudio.erp.message_queue.MessageQueueRepository;
import com.dancestudio.erp.repository.MessageRecipientRepository;
import com.dancestudio.erp.util.EmailUtil;
import com.dancestudio.erp.util.GenericTemplateUtil;
import com.dancestudio.erp.util.TempFileUtil;
import com.dancestudio.erp.util.TempFileUtil.FileData;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
public class EmailService {

    private final MessageQueueRepository repository;
    private final MessageRecipientRepository messageRecipientRepository;
    private final EmailUtil emailUtil;

    private final Lock emailLock = new ReentrantLock();

    public EmailService(MessageQueueRepository repository, EmailUtil emailUtil,
            MessageRecipientRepository messageRecipientRepository) {
        this.repository = repository;
        this.emailUtil = emailUtil;
        this.messageRecipientRepository = messageRecipientRepository;
    }

    private volatile boolean stop = false;

    private List<MessageQueue> findPendingEmails() {
        List<MessageQueue> messages = repository.findAllWithFileOnePerBranch(NotificationType.EMAIL.toString());
        if (messages.isEmpty()) {
            return repository.findAllOnePerBranch(NotificationType.EMAIL.toString());
        }
        return messages;
    }

    @Async
    public void processBulkEmails() {
        stop = false;

        while (!stop) {
            List<MessageQueue> messages = findPendingEmails();

            if (messages.isEmpty()) {
                stop = true;
                log.info("No pending emails. Exiting loop.");
                break;
            }

            emailLock.lock(); // Acquire lock for bulk processing
            try {
                for (MessageQueue msg : messages) {
                    processEachMail(msg);
                }
            } finally {
                emailLock.unlock(); // Release lock
            }

            log.info("Processed {} messages", messages.size());

            try {
                TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(2, 8));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Transactional
    public void processEachMail(MessageQueue msg) {
        try {
            Message message = msg.getMessage();
            Member member = msg.getMember();
            Branch branch = msg.getBranch();
            Studio studio = branch.getStudio();

            String content = GenericTemplateUtil.generateContentString(
                    message.getContent(), studio, branch, member);
            String subject = GenericTemplateUtil.generateContentString(
                    message.getTitle(), studio, branch, member);

            FileData fileData = TempFileUtil.getFile(msg.getFilePath());

            Boolean sent = emailUtil.sendEmail(member.getEmail(), subject, content, studio.getId(),
                    fileData.getFileBytes(), fileData.getOriginalName());

            if (sent) {
                MessageRecipient recipient = msg.getRecipient();
                if (Objects.nonNull(recipient)) {
                    recipient.setStatus(MessageStatus.SENT);
                    messageRecipientRepository.save(recipient);
                    repository.deleteById(msg.getId());
                }
                log.info("Message {} sent and deleted successfully", msg.getId());
            } else {
                msg.setRetries(msg.getRetries() + 1);
                repository.save(msg);
                log.warn("Message {} failed to send. Retry count: {}", msg.getId(), msg.getRetries());
            }

        } catch (Exception e) {
            msg.setRetries(msg.getRetries() + 1);
            repository.save(msg);
            log.error("Error sending message {}: {}", msg.getId(), e.getMessage(), e);

            try {
                TimeUnit.SECONDS.sleep(60);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * High-priority email sending (blocking)
     */
    public void sendHighPriorityEmail(String toEmail, String subject, String body, Long studioId,
            byte[] attachmentBytes, String attachmentFileName, MessageRecipient recipient) throws Exception {
        emailLock.lock(); // Acquire lock to prevent conflict with bulk processing
        try {
            emailUtil.sendEmail(toEmail, subject, body, studioId, attachmentBytes, attachmentFileName);
            if (Objects.nonNull(recipient)) {
                recipient.setStatus(MessageStatus.SENT);
                messageRecipientRepository.save(recipient);
            }
            log.info("High-priority email sent to {}", toEmail);
        } finally {
            emailLock.unlock(); // Release lock
        }
    }
}
