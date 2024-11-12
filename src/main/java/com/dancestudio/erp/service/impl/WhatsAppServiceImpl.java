package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.service.WhatsAppService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Setter
public class WhatsAppServiceImpl implements WhatsAppService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String fromPhoneNumber;

    @PostConstruct
    public void initTwilio() {
        Twilio.init(accountSid, authToken);
    }

    @Override
    public void sendWhatsAppMessage(String to, String messageText) {
        Message message = Message.creator(
                new PhoneNumber("whatsapp:+" + to),
                new PhoneNumber(fromPhoneNumber),
                messageText
        ).create();

        System.out.println("Message sent: " + message.getSid());
    }
}

