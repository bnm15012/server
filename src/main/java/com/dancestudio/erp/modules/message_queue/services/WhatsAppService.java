package com.dancestudio.erp.modules.message_queue.services;

import java.util.List;
import java.util.Objects;
// import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.dancestudio.erp.entity.MessageRecipient;
import com.dancestudio.erp.enums.MessageStatus;
import com.dancestudio.erp.enums.NotificationType;
import com.dancestudio.erp.modules.message_queue.MessageQueue;
import com.dancestudio.erp.modules.message_queue.MessageQueueRepository;
import com.dancestudio.erp.repository.MessageRecipientRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
public class WhatsAppService {

    private final MessageRecipientRepository messageRecipientRepository;

    private final MessageQueueRepository repository;
    // private final WhatsappUtil whatsappUtil;

    // Lock to prevent simultaneous sending of bulk and high-priority WhatsApp
    // messages
    private final Lock whatsappLock = new ReentrantLock();

    public WhatsAppService(MessageQueueRepository repository,
            MessageRecipientRepository messageRecipientRepository) {
        this.repository = repository;
        this.messageRecipientRepository = messageRecipientRepository;
    }

    private List<MessageQueue> findPendingWhatsAppMessages() {
        List<MessageQueue> messages = repository.findAllWithFileOnePerBranch(NotificationType.WHATSAPP.toString());
        if (messages.isEmpty()) {
            return repository.findAllOnePerBranch(NotificationType.WHATSAPP.toString());
        }
        return messages;
    }

    @Async
    public void processBulkWhatsAppMessages() {
        while (true) {
            List<MessageQueue> messages = findPendingWhatsAppMessages();

            if (messages.isEmpty()) {
                log.info("No pending WhatsApp messages. Exiting loop.");
                break;
            }

            whatsappLock.lock(); // Acquire lock for bulk processing
            try {
                for (MessageQueue msg : messages) {
                    processEachMessage(msg);
                }
            } finally {
                whatsappLock.unlock(); // Release lock
            }

            // try {
            //     TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10, 50));
            // } catch (InterruptedException e) {
            //     Thread.currentThread().interrupt();
            // }

            log.info("Processed {} messages", messages.size());
        }
    }

    @Transactional
    public void processEachMessage(MessageQueue msg) {
        try {
            // Message message = msg.getMessage();
            // Member member = msg.getMember();
            // Branch branch = msg.getBranch();
            // Studio studio = msg.getStudio();

            // String content = GenericTemplateUtil.generateContentString(
            //         message.getContent(), studio, branch, member);

            // FileData fileData = TempFileUtil.getFile(msg.getFilePath());
            Boolean sent = true;
            // Boolean sent = whatsappUtil.sendMessage(
            //         "91" + member.getPhone(), content, branch.getId(), fileData.getFileBytes(),
            //         fileData.getOriginalName(), fileData.getContentType());

            if (sent) {
                if (Objects.nonNull(msg.getRecipient())) {
                    MessageRecipient recipient = msg.getRecipient();
                    recipient.setStatus(MessageStatus.SENT);
                    messageRecipientRepository.save(recipient);
                }
                repository.deleteById(msg.getId());
                log.info("Message {} sent and deleted successfully", msg.getId());
            } else {
                throw new RuntimeException("WhatsApp message failed to send");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            msg.setRetries(msg.getRetries() + 1);
            log.error("Message {} failed to send. Retry count: {}", msg.getId(), msg.getRetries());
            try {
                if (msg.getRetries() > 3) {
                    MessageRecipient recipient = msg.getRecipient();
                    recipient.setStatus(MessageStatus.FAILED);
                    messageRecipientRepository.save(recipient);
                    repository.deleteById(msg.getId());
                } else {
                    repository.save(msg);
                }
                TimeUnit.SECONDS.sleep(60);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * High-priority WhatsApp message sending (blocking)
     */
    public void sendHighPriorityWhatsAppMessage(String to, String messageText, Long branchId,
            byte[] fileBytes, String originalName, String contentType, MessageRecipient recipient) {
        whatsappLock.lock(); // Acquire lock to prevent conflict with bulk processing
        try {
            // whatsappUtil.sendMessage(to, messageText, branchId, fileBytes, originalName, contentType);
            if (Objects.nonNull(recipient)) {
                recipient.setStatus(MessageStatus.SENT);
                messageRecipientRepository.save(recipient);
            }
            log.info("High-priority WhatsApp message sent to {}", to);
        } finally {
            whatsappLock.unlock(); // Release lock
        }
    }
}
