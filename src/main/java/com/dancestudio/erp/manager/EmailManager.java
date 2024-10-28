package com.dancestudio.erp.manager;

public interface EmailManager {

    void sendEmail(String to, String subject, String body);
}
