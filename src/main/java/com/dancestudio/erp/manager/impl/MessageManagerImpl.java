package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.*;
import com.dancestudio.erp.entry.MessageEntry;
import com.dancestudio.erp.entry.MessageRecipientEntry;
import com.dancestudio.erp.entry.SendMessageRequestEntry;
import com.dancestudio.erp.entry.SessionEntry;
import com.dancestudio.erp.enums.MessageStatus;
import com.dancestudio.erp.enums.TemplateType;
import com.dancestudio.erp.enums.WhatsAppStatus;
import com.dancestudio.erp.manager.MessageManager;
import com.dancestudio.erp.manager.NotificationManager;
import com.dancestudio.erp.repository.BranchRepository;
import com.dancestudio.erp.repository.MemberRepository;
import com.dancestudio.erp.repository.MessageRecipientRepository;
import com.dancestudio.erp.repository.MessageRepository;
import com.dancestudio.erp.response.SendMessageResponse;
import com.dancestudio.erp.util.WhatsappUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
@Slf4j
@Setter
public class MessageManagerImpl implements MessageManager {

    private final MessageRepository messageRepository;
    private final MessageRecipientRepository recipientRepository;
    private final MemberRepository memberRepository;
    private final BranchRepository branchRepository;

    @Autowired
    private NotificationManager notificationManager;
    @Autowired
    private WhatsappUtil whatsappUtil;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Transactional
    public SendMessageResponse sendMessage(SendMessageRequestEntry request) {
        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found"));

        boolean sendToAll = (request.getMemberIds() == null || request.getMemberIds().isEmpty());

        if (sendToAll) {
            // Async processing for all members
            executorService.submit(() -> {
                List<Member> members = memberRepository.findByBranchId(branch.getId());
                if (members.isEmpty()) {
                    log.error("No members found to send message.");
                    return;
                }
                Message message = createAndSaveMessage(request, branch);
                int success;
                if (isWhatsappNotification(request)) {
                    success = handleWhatsappNotification(request, branch, members, message);
                    handleEmailNotification(request, branch, members);
                } else if (isSmsNotification(request)) {
                    success = handleSmsNotification(request, branch, members, message);
                    handleEmailNotification(request, branch, members);
                } else {
                    success = handleEmailNotification(request, branch, members);
                }
                int failed = members.size() - success;
                log.info("Async message sending completed. Success: {}, Failed: {}", success, failed);
            });

            return new SendMessageResponse(0, 0, 0, "Please check after sometime. Message will be delivered soon");
        } else {
            // Synchronous processing for specific members
            List<Member> members = memberRepository.findAllById(request.getMemberIds());
            if (members.isEmpty()) {
                throw new RuntimeException("No members found to send message.");
            }
            Message message = createAndSaveMessage(request, branch);
            int success = 0;
            if (isWhatsappNotification(request)) {
                for (Member member : members) {
                    whatsappUtil.sendMessage("91" + member.getPhone(), message.getContent(), request.getBranchId());
                }
                 success = handleWhatsappNotification(request, branch, members, message);
            } else if (isSmsNotification(request)) {
                success = handleSmsNotification(request, branch, members, message);
                handleEmailNotification(request, branch, members);
            } else {
                success = handleEmailNotification(request, branch, members);
            }
            int failed = members.size() - success;
            return new SendMessageResponse(members.size(), success, failed, "Message sent successfully");
        }
    }

    @Override
    public List<MessageEntry> getMessagesByBranchId(Long branchId, int page, int size) {
        List<Message> messages;
        if (size == -1) {
            messages = messageRepository.findByBranchId(branchId).stream()
                    .sorted(Comparator.comparing(Message::getCreatedOn).reversed())
                    .toList();
        } else {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "lastModifiedOn"));
            messages = messageRepository.findByBranchId(branchId, pageable).getContent();
        }

        return messages.stream()
                .map(this::convertToMessageEntry)
                .toList();
    }

    @Override
    public long getMessageCountByBranchId(Long branchId) {
        return messageRepository.totalMessagesByBranchId(branchId);
    }

    @Override
    public List<MessageRecipientEntry> getMessageRecipients(Long messageId) {
        List<MessageRecipient> recipients = recipientRepository.findByMessageId(messageId);
        return recipients.stream()
                .map(recipient -> {
                    MessageRecipientEntry entry = new MessageRecipientEntry();
                    entry.setId(recipient.getId());
                    entry.setMemberId(recipient.getMember().getId());
                    entry.setName(recipient.getName());
                    entry.setPhoneNumber(recipient.getPhoneNumber());
                    entry.setStatus(recipient.getStatus());
                    entry.setReason(recipient.getReason());
                    return entry;
                })
                .toList();
    }

    @Override
    public SessionEntry createSession(Long branchId) {
        return whatsappUtil.createSession(branchId);
    }

    public MessageEntry convertToMessageEntry(Message message) {
        MessageEntry entry = new MessageEntry();
        entry.setId(message.getId());
        entry.setTitle(message.getTitle());
        entry.setContent(message.getContent());
        entry.setNotificationType(message.getNotiticationType());
        entry.setSentDate(message.getCreatedOn());
        entry.setBranchId(message.getBranch().getId());
        entry.setSentToAll(message.getSendToAll());

        return entry;
    }

    public Message createAndSaveMessage(SendMessageRequestEntry request, Branch branch) {
        Message message = new Message();
        message.setTitle(request.getTitle());
        message.setContent(request.getContent());
        message.setNotiticationType(request.getNotificationType());
        message.setBranch(branch);
        message.setSendToAll(request.getSentToAll());
        return messageRepository.save(message);
    }

    private int processRecipients(List<Member> members, Message message, String content) {

        int success = 0;
        for (Member member : members) {
            MessageRecipient recipient = new MessageRecipient();
            recipient.setMessage(message);
            recipient.setMember(member);
            recipient.setName(member.getName());
            recipient.setPhoneNumber(member.getPhone());
            recipient.setStatus(MessageStatus.PENDING);

            try {
                sendSms(member, content);
                recipient.setStatus(MessageStatus.SENT);
                success++;
            } catch (Exception e) {
                recipient.setStatus(MessageStatus.FAILED);
                recipient.setReason(e.getMessage());
            }

            recipientRepository.save(recipient);
        }

        return success;
    }

    private boolean isWhatsappNotification(SendMessageRequestEntry request) {
        return TemplateType.WHATSAPP.name().equals(request.getNotificationType());
    }

    private boolean isSmsNotification(SendMessageRequestEntry request) {
        return TemplateType.SMS.name().equals(request.getNotificationType());
    }

    private int handleWhatsappNotification(SendMessageRequestEntry request, Branch branch, List<Member> members, Message message) {
        if (WhatsAppStatus.ACTIVE.name().equals(branch.getWhatsappStatus())) {
            return processRecipients(members, message, request.getContent());
        }
        throw new RuntimeException("Studio not configured for WhatsApp messaging");
    }

    private int handleSmsNotification(SendMessageRequestEntry request, Branch branch, List<Member> members,
            Message message) {
        Studio studio = branch.getStudio();
        return 0;
    }

    private int handleEmailNotification(SendMessageRequestEntry request, Branch branch, List<Member> members) {
        int success = 0;
        for (Member member : members) {
            try {
                notificationManager.sendEmail(member.getEmail(), request.getTitle(), request.getContent(),
                        branch.getStudio().getId());
                success++;
            } catch (Exception e) {
                log.error("Failed to send email to student: {}", member.getEmail(), e);
            }
        }
        return success;
    }

    private void sendSms(Member member, String content) {
        if (member.getPhone() == null || member.getPhone().isBlank()) {
            throw new RuntimeException("Invalid phone number.");
        }

        whatsappUtil.sendMessage(member.getPhone(), content, member.getBranch().getId());
    }

    @Override
    public String checkStatus(Long branchId) {
        return branchRepository.findWhatsAppStatusByBranchId(branchId);
    }

    @Override
    public String logoutWhatsAppSession(Long branchId) {
        SessionEntry sessionEntry = whatsappUtil.logoutSession(branchId);
        return branchRepository.findWhatsAppStatusByBranchId(branchId);
    }
}
