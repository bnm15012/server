package com.dancestudio.erp.manager;

import com.dancestudio.erp.modules.member.Member;
import com.dancestudio.erp.modules.member.student.StudentActivityAssignment.StudentActivityAssignmentEntry;

public interface NotificationManager {

    void sendSubscriptionRenewalEmail(Member student, StudentActivityAssignmentEntry entry, String studioName);
}
