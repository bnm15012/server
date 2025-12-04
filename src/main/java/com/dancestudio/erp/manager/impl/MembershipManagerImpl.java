package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.Message;
import com.dancestudio.erp.entity.MessageRecipient;
import com.dancestudio.erp.entry.BranchEntry;
import com.dancestudio.erp.entry.StudentEntry;
import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.entry.TemplateEntry;
import com.dancestudio.erp.enums.MessageStatus;
import com.dancestudio.erp.enums.NotificationType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.*;
import com.dancestudio.erp.modules.message_queue.services.EmailService;
import com.dancestudio.erp.repository.MessageRecipientRepository;
import com.dancestudio.erp.repository.MessageRepository;
import com.dancestudio.erp.util.ConvertToEntryUtil;
import com.dancestudio.erp.util.DateUtil;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.dancestudio.erp.constants.TemplateName.SUBSCRIPTION_RENEWAL_REMINDER;

@Service
@Setter(onMethod = @__({ @Autowired }))
public class MembershipManagerImpl {

    private final EmailService emailService;

    private final MessageRecipientRepository messageRecipientRepository;
    private final MessageRepository messageRepository;

    private StudentManager studentManager;
    private BranchManager branchManager;
    private StudioManager studioManager;
    private TemplateManager templateManager;

    MembershipManagerImpl(EmailService emailService, MessageRecipientRepository messageRecipientRepository, MessageRepository messageRepository) {
        this.emailService = emailService;
        this.messageRecipientRepository = messageRecipientRepository;
        this.messageRepository = messageRepository;
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void sendMembershipRenewalReminders() throws EntityNotFoundException {
        Date reminderDate = DateUtil.addDays(DateUtil.getCurrentDateUTC(), 5);
        TemplateEntry templateEntry = templateManager.getTemplateDetails(SUBSCRIPTION_RENEWAL_REMINDER);

        List<StudentEntry> studentsToRemind = studentManager.findByMembershipEndDate(reminderDate);
        for (StudentEntry studentEntry : studentsToRemind) {
            try {
                BranchEntry branchEntry = branchManager.getById(studentEntry.getBranchId());
                StudioEntry studioEntry = studioManager.getById(branchEntry.getStudioId());
                Message message = messageRepository.save(new Message(ConvertToEntryUtil.convertToEntity(branchEntry, null), false,
                        templateEntry.getSubject(), templateEntry.getTemplateBody(),
                        NotificationType.EMAIL.name()));
                        
                MessageRecipient recipient = messageRecipientRepository
                        .save(new MessageRecipient(message,
                                studioEntry.getUserName(), studioEntry.getEmail(),
                                MessageStatus.PENDING, null));

                emailService.sendHighPriorityEmail(studentEntry.getEmail(), templateEntry.getSubject(), templateEntry.getTemplateBody(), studioEntry.getStudioId(), null, null, recipient);
            } catch (Exception e) {
                throw new EntityNotFoundException(e.getMessage());
            }

        }
    }
}
