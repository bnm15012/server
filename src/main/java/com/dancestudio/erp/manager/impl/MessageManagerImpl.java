package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.*;
import com.dancestudio.erp.entry.SendMessageRequestEntry;
import com.dancestudio.erp.enums.MessageStatus;
import com.dancestudio.erp.enums.MessageType;
import com.dancestudio.erp.manager.MessageManager;
import com.dancestudio.erp.manager.NotificationManager;
import com.dancestudio.erp.repository.*;
import com.dancestudio.erp.response.SendMessageResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
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

        List<Member> students = (request.getStudentIds() == null || request.getStudentIds().isEmpty()) ? memberRepository.findByBranchId(branch.getId()) : memberRepository.findAllById(request.getStudentIds());
        if (students.isEmpty()) {
            throw new RuntimeException("No students found to send message.");
        }

        Long currentMonth = (long) YearMonth.now().getMonthValue();
        StudioSmsUsage usage = studioSmsUsageRepository.findByBranchIdAndMonth(branch.getId(), currentMonth).orElse(null);

        if (Objects.isNull(usage)) {
            throw new RuntimeException("Not enough SMS balance. Available: " + 0);
        }

        int remainingQuota = usage.getQuota() - usage.getTotalSmsSent();
        if (students.size() > remainingQuota) {
            throw new RuntimeException("Not enough SMS balance. Available: " + remainingQuota);
        }

        Message message = new Message();
        message.setTitle(request.getTitle());
        message.setContent(request.getContent());
        message.setType(MessageType.valueOf(request.getType()));
        message.setBranch(branch);
        message.setSendToAll(request.getSentToAll());
        message = messageRepository.save(message);

        int success = 0, failed = 0;
        for (Member student : students) {
            MessageRecipient recipient = new MessageRecipient();
            recipient.setMessage(message);
            recipient.setMember(student);
            recipient.setPhoneNumber(student.getPhone());
            recipient.setStatus(MessageStatus.PENDING);

            try {
                sendSms(student.getPhone(), request.getContent());
//                notificationManager.sendEmail(student.getEmail(), request.getTitle(), request.getContent());

                recipient.setStatus(MessageStatus.SENT);
                recipient.setSentAt(new Date());
                success++;
            } catch (Exception e) {
                recipient.setStatus(MessageStatus.FAILED);
                failed++;
            }

            recipientRepository.save(recipient);
        }

        usage.setTotalSmsSent(usage.getTotalSmsSent() + success);
        studioSmsUsageRepository.save(usage);

        return new SendMessageResponse(students.size(), success, failed);
    }

    private void sendSms(String phoneNumber, String content) {
        // Integrate actual SMS gateway (e.g. Fast2SMS, MSG91, Twilio)
        // Simulating success for now
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new RuntimeException("Invalid phone number.");
        }
    }
}
