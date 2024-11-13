package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.Student;
import com.dancestudio.erp.entry.StudentActivityAssignmentEntry;
import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.entry.TemplateEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.EmailManager;
import com.dancestudio.erp.manager.StudioManager;
import com.dancestudio.erp.manager.TemplateManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static com.dancestudio.erp.constants.TemplateName.SUBSCRIPTION_RENEWAL_REMINDER;

@Service
@Slf4j
public class EmailManagerImpl implements EmailManager {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateManager templateManager;

    @Autowired
    private StudioManager studioManager;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    @Override
    public void sendSubscriptionRenewalEmail(Student student, StudentActivityAssignmentEntry entry) {
        try {
            TemplateEntry templateEntry = templateManager.getTemplateDetails(SUBSCRIPTION_RENEWAL_REMINDER);
            StudioEntry studioEntry = studioManager.getStudioById(entry.getActivity().getStudioId());

            String updatedBody = formatEmailBody(templateEntry, studioEntry.getStudioName(), student.getName(), entry.getActivity().getActivityType().name());
            sendEmail(student.getEmail(), templateEntry.getSubject(), updatedBody);
        } catch (MailException e) {
            log.error(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String formatEmailBody(TemplateEntry templateEntry, String studioName, String studentName, String activityType) {
        String updatedBody = templateEntry.getTemplateBody()
                .replace("{studio_name}", studioName)
                .replace("{student_name}", studentName)
                .replace("{activity_type}", activityType);
        return updatedBody;
    }

}
