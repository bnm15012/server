package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entry.StudentEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.EmailManager;
import com.dancestudio.erp.manager.StudentManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MembershipManagerImpl {

    @Autowired
    private StudentManager studentManager;

    @Autowired
    private EmailManager emailManager;

    @Scheduled(cron = "0 0 12 * * ?")
    public void sendMembershipRenewalReminders() throws EntityNotFoundException {
        LocalDate today = LocalDate.now();
        LocalDate reminderDate = today.plusDays(5);

        List<StudentEntry> studentsToRemind = studentManager.findByMembershipEndDate(reminderDate);
        for (StudentEntry studentEntry: studentsToRemind) {
            emailManager.sendEmail(studentEntry.getEmail(), "Membership Renewal Reminder", "Dear " + studentEntry.getName() + ",\n\nYour membership is about to expire. Please renew it soon!\n\nThank you!");
        }
    }


}
