package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entry.BranchEntry;
import com.dancestudio.erp.entry.StudentEntry;
import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.entry.TemplateEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.*;
import com.dancestudio.erp.util.DateUtil;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.dancestudio.erp.constants.TemplateName.SUBSCRIPTION_RENEWAL_REMINDER;

@Service
@Setter(onMethod = @__({@Autowired}))
public class MembershipManagerImpl {

    private StudentManager studentManager;
    private BranchManager branchManager;
    private StudioManager studioManager;
    private NotificationManager notificationManager;
    private TemplateManager templateManager;

    @Scheduled(cron = "0 0 12 * * ?")
    public void sendMembershipRenewalReminders() throws EntityNotFoundException {
        Date reminderDate = DateUtil.addDays(DateUtil.getCurrentDateUTC(), 5);
        TemplateEntry templateEntry = templateManager.getTemplateDetails(SUBSCRIPTION_RENEWAL_REMINDER);

        List<StudentEntry> studentsToRemind = studentManager.findByMembershipEndDate(reminderDate);
        for (StudentEntry studentEntry: studentsToRemind) {
            try {
                BranchEntry branchEntry = branchManager.getById(studentEntry.getBranchId());
                StudioEntry studioEntry = studioManager.getById(branchEntry.getStudioId());
                notificationManager.sendEmail(studentEntry.getEmail(), templateEntry.getSubject(), templateEntry.getTemplateBody(), studioEntry.getStudioId(), null, null);
            } catch (Exception e) {
                throw new EntityNotFoundException(e.getMessage());
            }

        }
    }
}
