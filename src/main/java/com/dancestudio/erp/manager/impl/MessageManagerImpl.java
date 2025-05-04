package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.*;
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

        List<Member> students = (request.getStudentIds() == null || request.getStudentIds().isEmpty())
                ? memberRepository.findByBranchId(branch.getId())
                : memberRepository.findAllById(request.getStudentIds());

        if (students.isEmpty()) {
            throw new RuntimeException("No students found to send message.");
        }

        Message message = createAndSaveMessage(request, branch);
        int success = 0;
        if (isWhatsappNotification(request)) {
            success = handleWhatsappNotification(request, branch, students, message);
            handleEmailNotification(request, branch, students);
        } else if (isSmsNotification(request)) {
            success = handleSmsNotification(request, branch, students, message);
            handleEmailNotification(request, branch, students);
        } else {
            success = handleEmailNotification(request, branch, students);
        }

        int failed = students.size() - success;
        return new SendMessageResponse(students.size(), success, failed);
    }

    private Message createAndSaveMessage(SendMessageRequestEntry request, Branch branch) {
        Message message = new Message();
        message.setTitle(request.getTitle());
        message.setContent(request.getContent());
        message.setNotiticationType(request.getNotiticationType());
        message.setBranch(branch);
        message.setSendToAll(request.getSentToAll());
        return messageRepository.save(message);
    }

    private int processRecipients(Studio studio, List<Member> students, Message message, String content) {

        int success = 0;
        for (Member student : students) {
            MessageRecipient recipient = new MessageRecipient();
            recipient.setMessage(message);
            recipient.setMember(student);
            recipient.setName(student.getName());
            recipient.setPhoneNumber(student.getPhone());
            recipient.setStatus(MessageStatus.PENDING);

            try {
                sendSms(studio, student, content);
                recipient.setStatus(MessageStatus.SENT);
                success++;
            } catch (Exception e) {
                recipient.setStatus(MessageStatus.FAILED);
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

    private int handleWhatsappNotification(SendMessageRequestEntry request, Branch branch, List<Member> students, Message message) {
        Studio studio = branch.getStudio();
        if(StringUtils.isEmpty(studio.getToken()) || StringUtils.isEmpty(studio.getInstanceId())) {
            throw new RuntimeException("Studio not configured for WhatsApp messaging");
        }

        return processRecipients(studio, students, message, request.getContent());
    }

    private int handleSmsNotification(SendMessageRequestEntry request, Branch branch, List<Member> students, Message message) {
        Studio studio = branch.getStudio();
        return 0;
    }

    private int handleEmailNotification(SendMessageRequestEntry request, Branch branch, List<Member> students) {
        int success = 0;
        for (Member student : students) {
            try {
                notificationManager.sendEmail(student.getEmail(), request.getTitle(), request.getContent(), branch.getStudio().getId());
                success++;
            } catch (Exception e) {
                log.error("Failed to send email to student: {}", student.getEmail(), e);
            }
        }
        return success;
    }

    private void sendSms(Studio studio, Member student, String content) {
        if (student.getPhone() == null || student.getPhone().isBlank()) {
            throw new RuntimeException("Invalid phone number.");
        }

        String token = studio.getToken();
        String instanceId = studio.getInstanceId();
        ultraMsgUtil.sendMessage(token, instanceId, student.getPhone(), content);
    }
}
