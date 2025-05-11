package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.SendMessageRequestEntry;
import com.dancestudio.erp.response.MessageRecipientResponse;
import com.dancestudio.erp.response.MessageResponse;
import com.dancestudio.erp.response.SendMessageResponse;
import org.springframework.http.ResponseEntity;

public interface MessageService {

    void sendWhatsAppMessage(String to, String messageText);

    ResponseEntity<SendMessageResponse> sendMessage(SendMessageRequestEntry request);

    ResponseEntity<MessageResponse> getMessagesByBranchId(Long branchId, int page, int size);

    ResponseEntity<MessageRecipientResponse> getMessageRecipients(Long messageId);
}
