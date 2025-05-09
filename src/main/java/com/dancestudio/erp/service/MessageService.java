package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.SendMessageRequestEntry;
import com.dancestudio.erp.response.SendMessageResponse;
import org.springframework.http.ResponseEntity;

public interface MessageService {

    void sendWhatsAppMessage(String to, String messageText);

    ResponseEntity<SendMessageResponse> sendMessage(SendMessageRequestEntry request);

}
