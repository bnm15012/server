package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.Branch;
import com.dancestudio.erp.entity.Client;
import com.dancestudio.erp.entity.Member;
import com.dancestudio.erp.entity.Message;
import com.dancestudio.erp.entity.Studio;
import com.dancestudio.erp.entry.SendMessageRequestEntry;
import com.dancestudio.erp.entry.StudentActivityAssignmentEntry;
import com.dancestudio.erp.entry.TemplateEntry;
import com.dancestudio.erp.manager.NotificationManager;
import com.dancestudio.erp.manager.TemplateManager;
import com.dancestudio.erp.repository.BranchRepository;
import com.dancestudio.erp.repository.ClientRepository;
import com.dancestudio.erp.repository.MemberRepository;
import com.dancestudio.erp.repository.MessageRepository;
import com.dancestudio.erp.repository.StudioRepository;
import com.dancestudio.erp.util.GenericTemplateUtil;

import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import static com.dancestudio.erp.constants.TemplateName.SUBSCRIPTION_RENEWAL_REMINDER;
import org.springframework.mail.javamail.MimeMessageHelper;

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
    @Autowired private MemberRepository memberRepository;
    @Autowired private BranchRepository branchRepository;
    @Autowired private MessageRepository messageRepository;
    @Autowired private ClientRepository clientRepository;

    public void sendEmail(String to, String subject, String body, Long studioId,
            byte[] attachmentBytes, String attachmentFileName) throws Exception {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setPort(mailPort);
        mailSender.setProtocol("smtp");
        mailSender.setDefaultEncoding(defaultEncoding);

        if (studioId != null) {
        Studio studio = studioRepository.findById(studioId).orElseThrow(() -> new
        Exception("Studio not found"));
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
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, attachmentBytes
        != null);

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

    @Override
    public void sendSubscriptionRenewalEmail(Member student, StudentActivityAssignmentEntry entry, String studioName) {
        try {
            TemplateEntry templateEntry = templateManager.getTemplateDetails(SUBSCRIPTION_RENEWAL_REMINDER);

            String updatedBody = formatEmailBody(templateEntry, studioName, student.getName(), entry.getActivityName());
            sendEmail(student.getEmail(), templateEntry.getSubject(), updatedBody,
                    student.getBranch().getStudio().getId(), null, null);
        } catch (MailException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendEmail(SendMessageRequestEntry requestEntry, byte[] attachmentBytes, String attachmentFileName)
            throws Exception {

        List<Member> members = requestEntry.getMemberIds() == null ? new ArrayList<>()
                : requestEntry.getSentToAll()
                        ? memberRepository.findByBranchId(requestEntry.getBranchId())
                        : memberRepository.findAllById(requestEntry.getMemberIds());

        List<Client> clients = requestEntry.getClientIds() == null ? new ArrayList<>()
                : clientRepository.findAllById(requestEntry.getClientIds());

        if (members.isEmpty() && clients.isEmpty()) {
            log.error("No members or client found to send message.");
            return;
        }

        Branch branch = branchRepository.findBranchWithStudioById(requestEntry.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found"));
        Studio studio = branch.getStudio();

        boolean saveFlag = true;
        if (Objects.nonNull(requestEntry.getTemplateName()) && StringUtils.hasText(requestEntry.getContent())) {
            TemplateEntry templateEntry = templateManager.getTemplateDetails(requestEntry.getTemplateName());
            requestEntry.setContent(templateEntry.getTemplateBody());
            saveFlag = false;
        }

        Long studioId = branch.getStudio().getId();

        Message message = createAndSaveMessage(requestEntry, branch, saveFlag);
        for (Member member : members) {
            if (Objects.isNull(requestEntry.getTemplateName())) {
                String title = GenericTemplateUtil.generateContentString(message.getTitle(), studio, branch, member);
                String content = GenericTemplateUtil.generateContentString(message.getContent(), studio, branch,
                        member);
                sendEmail(member.getEmail(), title, content, studioId, attachmentBytes, attachmentFileName);
            } else {
                TemplateEntry templateEntry = templateManager.getTemplateDetails(requestEntry.getTemplateName());
                String updatedBody = formatEmailBody(templateEntry, studio.getName(), member.getName(),
                        requestEntry.getActivityType());
                sendEmail(member.getEmail(), templateEntry.getSubject(), updatedBody, studioId, attachmentBytes,
                        attachmentFileName);
            }
        }
        for (Client client : clients) {
            TemplateEntry templateEntry = templateManager.getTemplateDetails(requestEntry.getTemplateName());
            String updatedBody = formatEmailBody(templateEntry, studio.getName(), client.getPocName(),
                    requestEntry.getActivityType());
            sendEmail(client.getPocEmail(), templateEntry.getSubject(), updatedBody, studioId, attachmentBytes,
                    attachmentFileName);
        }
    }

    public Message createAndSaveMessage(SendMessageRequestEntry request, Branch branch, boolean saveMessage) {
        Message message = new Message();
        message.setTitle(request.getTitle());
        message.setContent(request.getContent());
        message.setNotiticationType(request.getNotificationType());
        message.setBranch(branch);
        message.setSendToAll(request.getSentToAll());

        if (saveMessage) {
            return messageRepository.save(message);
        }

        return message;
    }

    private String formatEmailBody(TemplateEntry templateEntry, String studioName, String studentName,
            String activityType) {
        return templateEntry.getTemplateBody()
                .replace("{studio_name}", studioName)
                .replace("{student_name}", studentName)
                .replace("{activity_type}", activityType != null ? activityType : "");
    }

}
