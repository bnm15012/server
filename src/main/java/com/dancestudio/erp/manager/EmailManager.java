package com.dancestudio.erp.manager;

import com.dancestudio.erp.entity.Student;
import com.dancestudio.erp.entry.StudentActivityAssignmentEntry;

public interface EmailManager {

    void sendEmail(String to, String subject, String body);

    void sendSubscriptionRenewalEmail(Student student, StudentActivityAssignmentEntry entry);

}
