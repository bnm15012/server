package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.entry.*;
import com.dancestudio.erp.manager.MessageManager;
import com.dancestudio.erp.manager.NotificationManager;
import com.dancestudio.erp.response.*;
import com.dancestudio.erp.service.MessageService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Setter
public class MessageServiceImpl implements MessageService {

    @Autowired private MessageManager messageManager;
    @Autowired private NotificationManager notificationManager;

    @Override
    public void sendWhatsAppMessage(String to, String messageText) {
    }

    @Override
    public ResponseEntity<SendMessageResponse> sendMessage(SendMessageRequestEntry request) {

        SendMessageResponse response = new SendMessageResponse();
        try {
            SendMessageResponse result = messageManager.sendMessage(request);
            response.setStatus(new StatusResponse(1, "Message sent successfully", StatusResponse.Type.SUCCESS, Objects.isNull(result) ? 0 : 1));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<MessageResponse> getMessagesByBranchId(Long branchId, int page, int size) {
        MessageResponse response = new MessageResponse();
        try {
            List<MessageEntry> messageEntries = messageManager.getMessagesByBranchId(branchId, page, size);
            long totalMessages = messageManager.getMessageCountByBranchId(branchId);
            response.setStatus(new StatusResponse(1, "Messages fetched successfully", StatusResponse.Type.SUCCESS, (int) totalMessages));
            response.setData(messageEntries);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<MessageRecipientResponse> getMessageRecipients(Long messageId) {
        MessageRecipientResponse response = new MessageRecipientResponse();
        try {
            List<MessageRecipientEntry> messageEntries = messageManager.getMessageRecipients(messageId);
            response.setStatus(new StatusResponse(1, "Message recipients fetched successfully", StatusResponse.Type.SUCCESS, messageEntries.size()));
            response.setData(messageEntries);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<CreateSessionResponse> createSession(Long branchId) {
        CreateSessionResponse response = new CreateSessionResponse();
        try {
            SessionEntry sessionEntry = messageManager.createSession(branchId);
            response.setStatus(new StatusResponse(1, "Whatsapp session created successfully", StatusResponse.Type.SUCCESS));
            response.setData(Collections.singletonList(sessionEntry));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<WhatsAppStatusResponse> checkStatus(Long branchId) {
        WhatsAppStatusResponse response = new WhatsAppStatusResponse();
        try {
            String status = messageManager.checkStatus(branchId);
            WhatsAppStatusEntry whatsAppStatusEntry = new WhatsAppStatusEntry();
            whatsAppStatusEntry.setWebWhatsAppStatus(status);
            response.setStatus(new StatusResponse(1, "Whatsapp session status retrived successfully", StatusResponse.Type.SUCCESS));
            response.setData(Collections.singletonList(whatsAppStatusEntry));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<WhatsAppStatusResponse> logoutWhatsAppSession(Long branchId) {
        WhatsAppStatusResponse response = new WhatsAppStatusResponse();
        try {
            String status = messageManager.logoutWhatsAppSession(branchId);
            WhatsAppStatusEntry whatsAppStatusEntry = new WhatsAppStatusEntry();
            whatsAppStatusEntry.setWebWhatsAppStatus(status);
            response.setStatus(new StatusResponse(1, "Whatsapp session closed successfully", StatusResponse.Type.SUCCESS));
            response.setData(Collections.singletonList(whatsAppStatusEntry));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}

