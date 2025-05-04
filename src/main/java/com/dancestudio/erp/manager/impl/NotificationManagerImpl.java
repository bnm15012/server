package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.Member;
import com.dancestudio.erp.entity.Studio;
import com.dancestudio.erp.entry.StudentActivityAssignmentEntry;
import com.dancestudio.erp.entry.TemplateEntry;
import com.dancestudio.erp.manager.NotificationManager;
import com.dancestudio.erp.manager.TemplateManager;
import com.dancestudio.erp.repository.StudioRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Properties;

import static com.dancestudio.erp.constants.TemplateName.SUBSCRIPTION_RENEWAL_REMINDER;

@Service
@Slf4j
@Setter
public class NotificationManagerImpl implements NotificationManager {

    @Value("${spring.mail.host}")
    private String mailHost;
    @Value("${spring.mail.port}")
    private int mailPort;
    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String starttls;
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String auth;
    @Value("${spring.mail.username}")
    private String emailName;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.default-encoding}")
    private String defaultEncoding;

    @Autowired private TemplateManager templateManager;
    @Autowired private StudioRepository studioRepository;

    public void sendEmail(String to, String subject, String body, Long studioId) throws Exception {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setPort(mailPort);
        mailSender.setProtocol("smtp");
        mailSender.setDefaultEncoding(defaultEncoding);

        if(studioId != null) {
            Studio studio = studioRepository.findById(studioId).get();
            if(StringUtils.isEmpty(studio.getPasscode()))
                return;

            mailSender.setUsername(studio.getEmail());
            mailSender.setPassword(studio.getPasscode());
        } else {
            mailSender.setUsername(emailName);
            mailSender.setPassword(password);
        }

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", starttls);
        props.put("mail.debug", "false");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    @Override
    public void sendSubscriptionRenewalEmail(Member student, StudentActivityAssignmentEntry entry, String studioName) {
        try {
            TemplateEntry templateEntry = templateManager.getTemplateDetails(SUBSCRIPTION_RENEWAL_REMINDER);

            String updatedBody = formatEmailBody(templateEntry, studioName, student.getName(), entry.getActivity().getActivityType().name());
            sendEmail(student.getEmail(), templateEntry.getSubject(), updatedBody, student.getBranch().getStudio().getId());
        } catch (MailException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String formatEmailBody(TemplateEntry templateEntry, String studioName, String studentName, String activityType) {
        return templateEntry.getTemplateBody()
                .replace("{studio_name}", studioName)
                .replace("{student_name}", studentName)
                .replace("{activity_type}", activityType);
    }

}
