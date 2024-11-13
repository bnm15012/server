package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entry.StudentEntry;
import com.dancestudio.erp.entry.TemplateEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.EmailManager;
import com.dancestudio.erp.manager.StudentManager;
import com.dancestudio.erp.manager.TemplateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.dancestudio.erp.constants.TemplateName.SUBSCRIPTION_RENEWAL_REMINDER;

@Service
public class MembershipManagerImpl {

    @Autowired
    private StudentManager studentManager;

    @Autowired
    private EmailManager emailManager;

    @Autowired
    private TemplateManager templateManager;

    @Scheduled(cron = "0 0 12 * * ?")
    public void sendMembershipRenewalReminders() throws EntityNotFoundException {
        LocalDate today = LocalDate.now();
        LocalDate reminderDate = today.plusDays(5);

        TemplateEntry templateEntry = templateManager.getTemplateDetails(SUBSCRIPTION_RENEWAL_REMINDER);

        List<StudentEntry> studentsToRemind = studentManager.findByMembershipEndDate(reminderDate);
        for (StudentEntry studentEntry: studentsToRemind) {
            emailManager.sendEmail(studentEntry.getEmail(), templateEntry.getSubject(), templateEntry.getTemplateBody());
        }
    }


}
