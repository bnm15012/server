package com.dancestudio.erp.util;

import com.dancestudio.erp.entity.Studio;
import com.dancestudio.erp.repository.StudioRepository;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Properties;

@Component
@Setter
@Slf4j
public class EmailUtil {

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

    @Value("${spring.mail.debug}")
    private String sendMail;

    @Autowired
    private StudioRepository studioRepository;

    public Boolean sendEmail(String to, String subject, String body, Long studioId,
            byte[] attachmentBytes, String attachmentFileName) throws Exception {

        if (Boolean.valueOf(sendMail)) {
            log.info("Sending email - to: {}, subject: {}, body: {}, studioId: {}, attachmentFileName: {}", to, subject, body, studioId, attachmentFileName);
        } else {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost(mailHost);
            mailSender.setPort(mailPort);
            mailSender.setProtocol("smtp");
            mailSender.setDefaultEncoding(defaultEncoding);

            if (studioId != null) {
                Studio studio = studioRepository.findById(studioId).orElseThrow(() -> new Exception("Studio not found"));
                if (!StringUtils.hasText(studio.getPasscode())) {
                    throw new Exception("Studio email passcode is missing");
                }
                mailSender.setUsername(studio.getEmail());
                mailSender.setPassword(studio.getPasscode());
            } else {
                mailSender.setUsername(emailName);
                mailSender.setPassword(password);
            }

            // Mail properties
            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", auth);
            props.put("mail.smtp.starttls.enable", starttls);
            props.put("mail.debug", "false");

            // Always use MimeMessage for consistency
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, attachmentBytes != null);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, false); // false = plain text, true = HTML

            if (attachmentBytes != null && attachmentFileName != null) {
                helper.addAttachment(
                        attachmentFileName,
                        new ByteArrayDataSource(attachmentBytes, "application/octet-stream"));
            }

            mailSender.send(mimeMessage);
        }
        return true;
    }
}
