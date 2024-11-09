package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.Student;
import com.dancestudio.erp.entry.StudentActivityAssignmentEntry;
import com.dancestudio.erp.manager.EmailManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class EmailManagerImpl implements EmailManager {

    @Autowired
    private JavaMailSender mailSender;

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
            LocalDate endDate = entry.getMembershipEndDate();
            String formattedEndDate = formatDateWithSuffix(endDate);

            String subject = "Subscription Renewal Request";

            String body = "Dear " + student.getName() + ",\n\n"
                    + "This is a gentle reminder that your subscription is due for renewal soon. "
                    + "Please renew your " + entry.getActivity().getActivityType().name() + "membership before " + formattedEndDate + " to continue enjoying our services.\n\n"
                    + "Thank you,\nDance Studio Team";

            sendEmail(student.getEmail(), subject, body);
        } catch (MailException e) {
            log.error(e.getMessage());
        }
    }

    private String formatDateWithSuffix(LocalDate date) {
        int dayOfMonth = date.getDayOfMonth();
        String suffix = getDaySuffix(dayOfMonth);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM, yyyy");
        return dayOfMonth + suffix + " " + date.format(formatter).substring(2);
    }

    private String getDaySuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }
}
