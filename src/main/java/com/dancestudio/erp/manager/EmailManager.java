package com.dancestudio.erp.manager;

import com.dancestudio.erp.entity.Student;

public interface EmailManager {

    void sendEmail(String to, String subject, String body);

    void sendRegistrationEmail(Student student);

    void sendSubscriptionRenewalEmail(Student student);

}
