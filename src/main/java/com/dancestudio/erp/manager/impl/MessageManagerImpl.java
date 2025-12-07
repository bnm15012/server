package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.*;
import com.dancestudio.erp.entry.MessageEntry;
import com.dancestudio.erp.entry.MessageRecipientEntry;
import com.dancestudio.erp.entry.SendMessageRequestEntry;
import com.dancestudio.erp.entry.SessionEntry;
import com.dancestudio.erp.enums.MemberType;
import com.dancestudio.erp.enums.MessageStatus;
import com.dancestudio.erp.enums.NotificationType;
import com.dancestudio.erp.enums.TemplateType;
import com.dancestudio.erp.enums.WhatsAppStatus;
import com.dancestudio.erp.manager.MessageManager;
import com.dancestudio.erp.modules.template.template.TemplateEntry;
import com.dancestudio.erp.modules.template.template.TemplateManager;
import com.dancestudio.erp.modules.client.Client;
import com.dancestudio.erp.modules.client.ClientRepository;
import com.dancestudio.erp.modules.message_queue.MessageQueue;
import com.dancestudio.erp.modules.message_queue.MessageQueueRepository;
import com.dancestudio.erp.modules.message_queue.events.EmailQueuedEvent;
import com.dancestudio.erp.modules.message_queue.events.WhatsAppMessageQueuedEvent;
import com.dancestudio.erp.modules.message_queue.services.EmailService;
import com.dancestudio.erp.modules.message_queue.services.WhatsAppService;
import com.dancestudio.erp.repository.BranchRepository;
import com.dancestudio.erp.repository.MemberRepository;
import com.dancestudio.erp.repository.MessageRecipientRepository;
import com.dancestudio.erp.repository.MessageRepository;
import com.dancestudio.erp.repository.StudioRepository;
import com.dancestudio.erp.response.SendMessageResponse;
import com.dancestudio.erp.util.TempFileUtil;
import com.dancestudio.erp.util.WhatsappUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
@Setter
public class MessageManagerImpl implements MessageManager {

    private final WhatsAppService whatsAppService;

    private final EmailService emailService;
    private final MessageQueueRepository messageQueueRepository;
    private final MessageRepository messageRepository;
    private final MessageRecipientRepository recipientRepository;
    private final MemberRepository memberRepository;
    private final BranchRepository branchRepository;
    private final StudioRepository studioRepository;
    private final ClientRepository clientRepository;

    @Autowired
    private TemplateManager templateManager;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private WhatsappUtil whatsappUtil;

    @Transactional
    public SendMessageResponse sendMessage(SendMessageRequestEntry request, byte[] fileBytes, String originalName,
            String contentType)
            throws Exception {
        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found"));

        MemberType memberType = (request.getMemberType());

        isWhatsappNotification(request);
        isEmailNotification(request, branch);

        try {
            List<Member> members = new ArrayList<>();
            if (Objects.nonNull(memberType)) {
                if (memberType.equals(MemberType.ALL)) {
                    members = memberRepository.findByBranchId(branch.getId());
                } else {
                    members = memberRepository.findByBranchIdAndMemberType(branch.getId(), memberType.name());
                }
            } else if (Objects.nonNull(request.getMemberIds())) {
                members = memberRepository.findAllById(request.getMemberIds());
            }

            List<Client> clients = request.getClientIds() == null ? new ArrayList<>()
                    : clientRepository.findAllById(request.getClientIds());

            if (members.isEmpty() && clients.isEmpty()) {
                log.error("No members or client found to send message.");
                throw new RuntimeException("No members or client found to send message.");
            }

            Message message = createAndSaveMessage(request, branch);
            int success = 0;

            if (isWhatsappNotification(request)) {
                success = sendWhatsAppMessagesWithDelay(members, clients, message, branch, fileBytes, originalName,
                        contentType);
            } else if (isEmailNotification(request, branch)) {
                sendEmail(request, members, clients, fileBytes, originalName, message, contentType);
                success = members.size();
            } else if (isSmsNotification(request)) {
                success = handleSmsNotification(request, branch, members, message);
            } else {
                throw new RuntimeException("Unsupported notification type: " + request.getNotificationType());
            }

            int failed = members.size() - success;
            log.info("Async message sending completed. Success: {}, Failed: {}", success, failed);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return new SendMessageResponse(0, 0, 0,
                "Messages are being sent in the background. Please check back later for status.", new ArrayList<>());
    }

    private void sendEmail(SendMessageRequestEntry requestEntry, List<Member> members, List<Client> clients,
            byte[] fileBytes, String originalName, Message message, String contentType)
            throws Exception {

        Branch branch = branchRepository.findWithStudioById(requestEntry.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found"));
        Studio studio = branch.getStudio();

        if (Objects.nonNull(requestEntry.getTemplateName()) && !StringUtils.isAllEmpty(requestEntry.getContent())) {
            TemplateEntry templateEntry = templateManager.getTemplateDetails(requestEntry.getTemplateName());
            requestEntry.setContent(templateEntry.getTemplateBody());
        }

        Long studioId = branch.getStudio().getId();

        for (Member member : members) {
            if (Objects.nonNull(requestEntry.getTemplateName())) {
                TemplateEntry templateEntry = templateManager.getTemplateDetails(requestEntry.getTemplateName());
                String updatedBody = formatEmailBody(templateEntry, studio.getName(), member.getName(),
                        requestEntry.getActivityType());
                message.setContent(updatedBody);
                messageRepository.save(message);
                MessageRecipient recipient = recipientRepository
                        .save(new MessageRecipient(message,
                                member.getName(), member.getEmail(),
                                MessageStatus.PENDING, null));
                emailService.sendHighPriorityEmail(member.getEmail(), templateEntry.getSubject(), updatedBody, studioId,
                        fileBytes,
                        originalName, recipient);
            }
        }
        if (Objects.isNull(requestEntry.getTemplateName())) {
            String filePath = fileBytes != null ? TempFileUtil.saveTempFile(fileBytes, originalName, contentType)
                    : null;
            if (addToMemberTOQueue(members, message, branch, filePath, NotificationType.EMAIL)) {
                publisher.publishEvent(new EmailQueuedEvent());
            }
        }
        for (Client client : clients) {
            TemplateEntry templateEntry = templateManager.getTemplateDetails(requestEntry.getTemplateName());
            String updatedBody = formatEmailBody(templateEntry, studio.getName(), client.getPocName(),
                    requestEntry.getActivityType());
            message.setContent(updatedBody);
            messageRepository.save(message);
            MessageRecipient recipient = recipientRepository
                    .save(new MessageRecipient(message, client.getPocName(), client.getPocPhone(),
                            MessageStatus.PENDING, null));

            emailService.sendHighPriorityEmail(client.getPocEmail(), templateEntry.getSubject(), updatedBody, studioId,
                    fileBytes,
                    originalName, recipient);
        }
    }

    private int sendWhatsAppMessagesWithDelay(List<Member> members, List<Client> clients, Message message,
            Branch branch, byte[] fileBytes,
            String originalName, String contentType) throws IOException {

        int success = 0;
        if (!clients.isEmpty()) {
            log.info("Sending WhatsApp messages with attachment to {} members and {} clients",
                    members.size(), clients.size());
            for (Client client : clients) {
                try {
                    MessageRecipient recipient = recipientRepository
                            .save(new MessageRecipient(message, client.getPocName(), client.getPocPhone(),
                                    MessageStatus.PENDING, null));

                    whatsAppService.sendHighPriorityWhatsAppMessage(
                            "91" + client.getPocPhone(), message.getContent(), branch.getId(),
                            fileBytes, originalName, contentType, recipient);
                    success++;
                } catch (Exception e) {
                    log.error("Error sending WhatsApp message to {}: {}", client.getPocPhone(), e.getMessage());
                }
            }
        }
        log.info("Sending WhatsApp messages to {} members and {} clients",
                members.size(), clients.size());
        String filePath = fileBytes != null ? TempFileUtil.saveTempFile(fileBytes, originalName, contentType) : null;
        if (addToMemberTOQueue(members, message, branch, filePath, NotificationType.WHATSAPP)) {
            success = members.size();
            publisher.publishEvent(new WhatsAppMessageQueuedEvent());
        }

        return success;
    }

    private Boolean addToMemberTOQueue(List<Member> members, Message message, Branch branch, String filePath,
            NotificationType notificationType) {
        try {
            for (Member member : members) {
                MessageRecipient recipient = recipientRepository.save(new MessageRecipient(message, member.getName(),
                        notificationType.equals(NotificationType.EMAIL) ? member.getEmail() : member.getPhone(),
                        MessageStatus.PENDING, null));
                MessageQueue messageQueue = new MessageQueue();
                messageQueue.setMember(member);
                messageQueue.setMessage(message);
                messageQueue.setBranch(branch);
                messageQueue.setStudio(branch.getStudio());
                messageQueue.setNotificationType(notificationType);
                messageQueue.setFilePath(filePath);
                messageQueue.setRecipient(recipient);
                messageQueueRepository.save(messageQueue);
            }
            return true;
        } catch (Exception e) {
            return false;
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
                    entry.setName(recipient.getName());
                    entry.setContact(recipient.getContact());
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
        if (Objects.isNull(request.getContent())) {
            message.setContent(request.getInvoiceUrl());
        } else {
            message.setContent(request.getContent());
        }
        message.setNotiticationType(request.getNotificationType());
        message.setBranch(branch);
        message.setSendToAll(Objects.nonNull(request.getMemberType()));
        return messageRepository.save(message);
    }

    private boolean isWhatsappNotification(SendMessageRequestEntry request) throws Exception {
        boolean isWhatsApp = TemplateType.WHATSAPP.name().equals(request.getNotificationType());
        if (isWhatsApp && checkStatus(request.getBranchId()).equals(WhatsAppStatus.INACTIVE.name())) {
            throw new RuntimeException("WhatsApp session is not active. Please create session first.");
        }
        if (isWhatsApp && checkStatus(request.getBranchId()).equals(WhatsAppStatus.LOGOUT.name())) {
            throw new RuntimeException("WhatsApp session is logout. Please re-configure session first.");
        }
        return isWhatsApp;
    }

    private boolean isSmsNotification(SendMessageRequestEntry request) {
        return TemplateType.SMS.name().equals(request.getNotificationType());
    }

    private boolean isEmailNotification(SendMessageRequestEntry request, Branch branch) {
        boolean isEmail = TemplateType.EMAIL.name().equals(request.getNotificationType());

        Studio studio = studioRepository.findById(branch.getStudio().getId())
                .orElseThrow(() -> new RuntimeException("Studio not found for branch: " + branch.getId()));

        if (isEmail && Objects.isNull(studio.getPasscode()) && StringUtils.isBlank(studio.getEmail())) {
            throw new RuntimeException("Email passcode isn't configured.");
        }

        return isEmail;

    }

    @Override
    public String checkStatus(Long branchId) {
        whatsappUtil.checkSessionStatus(branchId);
        return branchRepository.findProjectedById(branchId)
                .map(BranchRepository.WhatsAppStatusView::getWhatsappStatus)
                .orElse(null);
    }

    @Override
    public String logoutWhatsAppSession(Long branchId) {
        SessionEntry sessionEntry = whatsappUtil.logoutSession(branchId);
        if (sessionEntry == null || !sessionEntry.isSuccess()) {
            throw new RuntimeException("Failed to logout WhatsApp session for branch ID: " + branchId);
        }
        branchRepository.updateWhatsAppStatus(branchId, WhatsAppStatus.LOGOUT.name());
        return branchRepository.findProjectedById(branchId)
                .map(BranchRepository.WhatsAppStatusView::getWhatsappStatus)
                .orElse(null);
    }

    private int handleSmsNotification(SendMessageRequestEntry request, Branch branch, List<Member> members,
            Message message) {
        throw new UnsupportedOperationException("SMS notification is not implemented yet.");
    }

    private String formatEmailBody(TemplateEntry templateEntry, String studioName, String studentName,
            String activityType) {
        return templateEntry.getTemplateBody()
                .replace("{studio_name}", studioName)
                .replace("{student_name}", studentName)
                .replace("{activity_type}", activityType != null ? activityType : "");
    }
    // dead code
    // --------------------------------------------------------------------------------------------------------------------
    // private void sendSms(Member member, String content) {
    // if (member.getPhone() == null || member.getPhone().isBlank()) {
    // throw new RuntimeException("Invalid phone number.");
    // }

    // whatsappUtil.sendMessage(member.getPhone(), content,
    // member.getBranch().getId(), null, null, null);
    // }

    // private int processRecipients(List<Member> members, Message message, String
    // content, Boolean sendWhatsapp) {

    // int success = 0;
    // for (Member member : members) {
    // MessageRecipient recipient = new MessageRecipient();
    // recipient.setMessage(message);
    // recipient.setMember(member);
    // recipient.setName(member.getName());
    // recipient.setEmail(member.getEmail());
    // recipient.setPhoneNumber(member.getPhone());
    // recipient.setStatus(MessageStatus.PENDING);

    // try {
    // if (sendWhatsapp) {
    // sendSms(member, content);
    // }
    // recipient.setStatus(MessageStatus.SENT);
    // success++;
    // } catch (Exception e) {
    // recipient.setStatus(MessageStatus.FAILED);
    // recipient.setReason(e.getMessage());
    // }

    // recipientRepository.save(recipient);
    // }

    // return success;
    // }
    // private int handleEmailNotification(SendMessageRequestEntry request, Branch
    // branch, List<Member> members,
    // Message message) {
    // int success = 0;
    // for (Member member : members) {
    // try {
    // notificationManager.sendEmail(member.getEmail(), request.getTitle(),
    // request.getContent(),
    // branch.getStudio().getId());
    // success++;
    // } catch (Exception e) {
    // log.error("Failed to send email to student: {}", member.getEmail(), e);
    // }
    // }
    // processRecipients(members, message, request.getContent(), false);
    // return success;
    // }
}
