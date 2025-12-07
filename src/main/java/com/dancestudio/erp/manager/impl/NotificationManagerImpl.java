package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.Message;
import com.dancestudio.erp.entity.MessageRecipient;
import com.dancestudio.erp.enums.MessageStatus;
import com.dancestudio.erp.enums.NotificationType;
import com.dancestudio.erp.manager.NotificationManager;
import com.dancestudio.erp.modules.template.template.TemplateEntry;
import com.dancestudio.erp.modules.template.template.TemplateManager;
import com.dancestudio.erp.modules.member.Member;
import com.dancestudio.erp.modules.member.student.StudentActivityAssignmentEntry;
import com.dancestudio.erp.modules.message_queue.services.EmailService;
import com.dancestudio.erp.repository.MessageRepository;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import static com.dancestudio.erp.constants.TemplateName.SUBSCRIPTION_RENEWAL_REMINDER;

@Service
@Slf4j
@Setter
public class NotificationManagerImpl implements NotificationManager {

    private final EmailService emailService;
    private final MessageRepository messageRepository;

    @Autowired
    private TemplateManager templateManager;

    NotificationManagerImpl(EmailService emailService, MessageRepository messageRepository) {
        this.emailService = emailService;
        this.messageRepository = messageRepository;
    }

    @Override
    public void sendSubscriptionRenewalEmail(Member student, StudentActivityAssignmentEntry entry, String studioName) {
        try {
            TemplateEntry templateEntry = templateManager.getTemplateDetails(SUBSCRIPTION_RENEWAL_REMINDER);

            String updatedBody = formatEmailBody(templateEntry, studioName, student.getName(), entry.getActivityName());
            Message message2 = messageRepository.save(new Message(student.getBranch(), false,
                    templateEntry.getSubject(), templateEntry.getTemplateBody(),
                    NotificationType.EMAIL.name()));
            MessageRecipient recepient = new MessageRecipient(message2, student.getName(), student.getEmail(),
                    MessageStatus.PENDING, null);
            emailService.sendHighPriorityEmail(student.getEmail(), templateEntry.getSubject(), updatedBody,
                    student.getBranch().getStudio().getId(), null, null, recepient);
        } catch (MailException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String formatEmailBody(TemplateEntry templateEntry, String studioName, String studentName,
            String activityType) {
        return templateEntry.getTemplateBody()
                .replace("{studio_name}", studioName)
                .replace("{student_name}", studentName)
                .replace("{activity_type}", activityType != null ? activityType : "");
    }

}
