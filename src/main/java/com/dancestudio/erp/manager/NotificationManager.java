package com.dancestudio.erp.manager;

import com.dancestudio.erp.entity.Member;
import com.dancestudio.erp.entry.SendMessageRequestEntry;
import com.dancestudio.erp.entry.StudentActivityAssignmentEntry;

public interface NotificationManager {

    void sendEmail(String to, String subject, String body, Long studioId) throws Exception;

    void sendSubscriptionRenewalEmail(Member student, StudentActivityAssignmentEntry entry, String studioName);

    void sendEmail(SendMessageRequestEntry requestEntry) throws Exception;

}
