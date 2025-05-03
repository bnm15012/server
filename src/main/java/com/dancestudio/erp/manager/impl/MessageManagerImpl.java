package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.*;
import com.dancestudio.erp.entry.SendMessageRequestEntry;
import com.dancestudio.erp.enums.MessageStatus;
import com.dancestudio.erp.enums.TemplateType;
import com.dancestudio.erp.manager.MessageManager;
import com.dancestudio.erp.manager.NotificationManager;
import com.dancestudio.erp.repository.*;
import com.dancestudio.erp.response.SendMessageResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageManagerImpl implements MessageManager {

    private final MessageRepository messageRepository;
    private final MessageRecipientRepository recipientRepository;
    private final MemberRepository memberRepository;
    private final BranchRepository branchRepository;
    private final StudioSmsUsageRepository studioSmsUsageRepository;

    @Autowired private NotificationManager notificationManager;

    @Transactional
    public SendMessageResponse sendMessage(SendMessageRequestEntry request) {
        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new RuntimeException("Studio not found"));

        List<Member> students = (request.getStudentIds() == null || request.getStudentIds().isEmpty())
                ? memberRepository.findByBranchId(branch.getId())
                : memberRepository.findAllById(request.getStudentIds());

        if (students.isEmpty()) {
            throw new RuntimeException("No students found to send message.");
        }

        Message message = createAndSaveMessage(request, branch);
        int success = 0;
        if (isSmsNotification(request)) {
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
        message.setMessageType(request.getMessageType());
        message.setNotiticationType(request.getNotiticationType());
        message.setBranch(branch);
        message.setSendToAll(request.getSentToAll());
        return messageRepository.save(message);
    }

    private void validateSmsQuota(Branch branch, int studentCount) {
        Long currentMonth = (long) YearMonth.now().getMonthValue();

        StudioSmsUsage usage = studioSmsUsageRepository.findByBranchIdAndMonth(branch.getId(), currentMonth).orElse(null);
        if (Objects.isNull(usage)) {
            throw new RuntimeException("Not enough SMS balance. Available: " + 0);
        }

        long remainingQuota = usage.getQuota() - usage.getTotalSmsSent();
        if (studentCount > remainingQuota) {
            throw new RuntimeException("Not enough SMS balance. Available: " + remainingQuota);
        }
    }


    private int processRecipients(List<Member> students, Message message, String content) {

        int success = 0;
        for (Member student : students) {
            MessageRecipient recipient = new MessageRecipient();
            recipient.setMessage(message);
            recipient.setMember(student);
            recipient.setPhoneNumber(student.getPhone());
            recipient.setStatus(MessageStatus.PENDING);

            try {
                sendSms(student.getPhone(), content);
                recipient.setStatus(MessageStatus.SENT);
                recipient.setSentAt(new Date());
                success++;
            } catch (Exception e) {
                recipient.setStatus(MessageStatus.FAILED);
            }

            recipientRepository.save(recipient);
        }

        return success;
    }

    private void updateSmsUsage(Branch branch, int successCount) {
        Long currentMonth = (long) YearMonth.now().getMonthValue();
        StudioSmsUsage usage = studioSmsUsageRepository.findByBranchIdAndMonth(branch.getId(), currentMonth).orElse(null);

        if (usage != null) {
            usage.setTotalSmsSent(usage.getTotalSmsSent() + successCount);
            studioSmsUsageRepository.save(usage);
        }
    }

    private boolean isSmsNotification(SendMessageRequestEntry request) {
        return TemplateType.SMS.name().equals(request.getNotiticationType());
    }

    private int handleSmsNotification(SendMessageRequestEntry request, Branch branch, List<Member> students, Message message) {
        validateSmsQuota(branch, students.size());
        int success = processRecipients(students, message, request.getContent());
        updateSmsUsage(branch, success);
        return success;
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

    private void sendSms(String phoneNumber, String content) {
        // Integrate actual SMS gateway (e.g. Fast2SMS, MSG91, Twilio)
        // Simulating success for now
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new RuntimeException("Invalid phone number.");
        }
    }
}
