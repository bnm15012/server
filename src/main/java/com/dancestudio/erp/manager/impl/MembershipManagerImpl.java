package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entry.BranchEntry;
import com.dancestudio.erp.entry.StudentEntry;
import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.entry.TemplateEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.*;
import com.dancestudio.erp.util.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.dancestudio.erp.constants.TemplateName.SUBSCRIPTION_RENEWAL_REMINDER;

@Service
public class MembershipManagerImpl {

    @Autowired private StudentManager studentManager;
    @Autowired private BranchManager branchManager;
    @Autowired private StudioManager studioManager;
    @Autowired private NotificationManager notificationManager;
    @Autowired private TemplateManager templateManager;

    @Scheduled(cron = "0 0 12 * * ?")
    public void sendMembershipRenewalReminders() throws EntityNotFoundException {
        Date reminderDate = DateUtil.addDays(DateUtil.getCurrentDateUTC(), 5);
        TemplateEntry templateEntry = templateManager.getTemplateDetails(SUBSCRIPTION_RENEWAL_REMINDER);

        List<StudentEntry> studentsToRemind = studentManager.findByMembershipEndDate(reminderDate);
        for (StudentEntry studentEntry: studentsToRemind) {
            try {
                BranchEntry branchEntry = branchManager.getById(studentEntry.getBranchId());
                StudioEntry studioEntry = studioManager.getById(branchEntry.getStudioId());
                notificationManager.sendEmail(studentEntry.getEmail(), templateEntry.getSubject(), templateEntry.getTemplateBody(), studioEntry.getStudioId());
            } catch (Exception e) {
                throw new EntityNotFoundException(e.getMessage());
            }

        }
    }
}
