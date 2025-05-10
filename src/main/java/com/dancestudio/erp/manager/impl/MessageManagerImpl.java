package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.*;
import com.dancestudio.erp.entry.MessageEntry;
import com.dancestudio.erp.entry.MessageRecipientEntry;
import com.dancestudio.erp.entry.SendMessageRequestEntry;
import com.dancestudio.erp.enums.MessageStatus;
import com.dancestudio.erp.enums.TemplateType;
import com.dancestudio.erp.manager.MessageManager;
import com.dancestudio.erp.manager.NotificationManager;
import com.dancestudio.erp.repository.BranchRepository;
import com.dancestudio.erp.repository.MemberRepository;
import com.dancestudio.erp.repository.MessageRecipientRepository;
import com.dancestudio.erp.repository.MessageRepository;
import com.dancestudio.erp.response.SendMessageResponse;
import com.dancestudio.erp.util.UltraMsgUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Setter
public class MessageManagerImpl implements MessageManager {

    private final MessageRepository messageRepository;
    private final MessageRecipientRepository recipientRepository;
    private final MemberRepository memberRepository;
    private final BranchRepository branchRepository;

    @Autowired private NotificationManager notificationManager;
    @Autowired private UltraMsgUtil ultraMsgUtil;

    @Transactional
    public SendMessageResponse sendMessage(SendMessageRequestEntry request) {
        Branch branch = branchRepository.findById(request.getBranchId()).orElseThrow(() -> new RuntimeException("Branch not found"));

        List<Member> members = (request.getMemberIds() == null || request.getMemberIds().isEmpty())
                ? memberRepository.findByBranchId(branch.getId())
                : memberRepository.findAllById(request.getMemberIds());

        if (members.isEmpty()) {
            throw new RuntimeException("No members found to send message.");
        }

        Message message = createAndSaveMessage(request, branch);
        int success = 0;
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
        return new SendMessageResponse(members.size(), success, failed);
    }

    @Override
    public List<MessageEntry> getMessagesByBranchId(Long branchId) {
        List<Message> messages = messageRepository.findByBranchId(branchId);
        return messages.stream()
                .sorted((m1, m2) -> m2.getCreatedOn().compareTo(m1.getCreatedOn()))
                .map(this::convertToMessageEntry)
                .toList();
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
        message.setNotiticationType(request.getNotiticationType());
        message.setBranch(branch);
        message.setSendToAll(request.getSentToAll());
        return messageRepository.save(message);
    }

    private int processRecipients(Studio studio, List<Member> members, Message message, String content) {

        int success = 0;
        for (Member member : members) {
            MessageRecipient recipient = new MessageRecipient();
            recipient.setMessage(message);
            recipient.setMember(member);
            recipient.setName(member.getName());
            recipient.setPhoneNumber(member.getPhone());
            recipient.setStatus(MessageStatus.PENDING);

            try {
                sendSms(studio, member, content);
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
        return TemplateType.WHATSAPP.name().equals(request.getNotiticationType());
    }

    private boolean isSmsNotification(SendMessageRequestEntry request) {
        return TemplateType.SMS.name().equals(request.getNotiticationType());
    }

    private int handleWhatsappNotification(SendMessageRequestEntry request, Branch branch, List<Member> members, Message message) {
        Studio studio = branch.getStudio();
        if(StringUtils.isEmpty(studio.getToken()) || StringUtils.isEmpty(studio.getInstanceId())) {
            throw new RuntimeException("Studio not configured for WhatsApp messaging");
        }

        return processRecipients(studio, members, message, request.getContent());
    }

    private int handleSmsNotification(SendMessageRequestEntry request, Branch branch, List<Member> members, Message message) {
        Studio studio = branch.getStudio();
        return 0;
    }

    private int handleEmailNotification(SendMessageRequestEntry request, Branch branch, List<Member> members) {
        int success = 0;
        for (Member member : members) {
            try {
                notificationManager.sendEmail(member.getEmail(), request.getTitle(), request.getContent(), branch.getStudio().getId());
                success++;
            } catch (Exception e) {
                log.error("Failed to send email to student: {}", member.getEmail(), e);
            }
        }
        return success;
    }

    private void sendSms(Studio studio, Member member, String content) {
        if (member.getPhone() == null || member.getPhone().isBlank()) {
            throw new RuntimeException("Invalid phone number.");
        }

        String token = studio.getToken();
        String instanceId = studio.getInstanceId();
        ultraMsgUtil.sendMessage(token, instanceId, member.getPhone(), content);
    }
}
