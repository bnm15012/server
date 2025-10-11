package com.dancestudio.erp.manager;

import com.dancestudio.erp.entity.Member;
import com.dancestudio.erp.entry.StudentActivityAssignmentEntry;

public interface NotificationManager {

    void sendSubscriptionRenewalEmail(Member student, StudentActivityAssignmentEntry entry, String studioName);
}
