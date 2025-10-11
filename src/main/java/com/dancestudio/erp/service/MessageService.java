package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.SendMessageRequestEntry;
import com.dancestudio.erp.response.WhatsAppStatusResponse;
import com.dancestudio.erp.response.CreateSessionResponse;
import com.dancestudio.erp.response.MessageRecipientResponse;
import com.dancestudio.erp.response.MessageResponse;
import com.dancestudio.erp.response.SendMessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface MessageService {

    void sendWhatsAppMessage(String to, String messageText);

    ResponseEntity<SendMessageResponse> sendMessage(SendMessageRequestEntry request, MultipartFile file, Integer page,
            Integer size);

    ResponseEntity<MessageResponse> getMessagesByBranchId(Long branchId, int page, int size);

    ResponseEntity<MessageRecipientResponse> getMessageRecipients(Long messageId);

    ResponseEntity<CreateSessionResponse> createSession(Long branchId);

    ResponseEntity<WhatsAppStatusResponse> checkStatus(Long branchId);

    ResponseEntity<WhatsAppStatusResponse> logoutWhatsAppSession(Long branchId);

}
