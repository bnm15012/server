package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.entry.MessageEntry;
import com.dancestudio.erp.entry.MessageRecipientEntry;
import com.dancestudio.erp.entry.SendMessageRequestEntry;
import com.dancestudio.erp.manager.MessageManager;
import com.dancestudio.erp.manager.NotificationManager;
import com.dancestudio.erp.response.MessageRecipientResponse;
import com.dancestudio.erp.response.MessageResponse;
import com.dancestudio.erp.response.SendMessageResponse;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.service.MessageService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.dancestudio.erp.enums.TemplateType.EMAIL;

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
            if(request.getNotiticationType().equals(EMAIL.name())) {
                notificationManager.sendEmail(request);
                response.setStatus(new StatusResponse(1, "Email sent successfully", StatusResponse.Type.SUCCESS, 1));
            } else {
                SendMessageResponse result = messageManager.sendMessage(request);
                response.setStatus(new StatusResponse(1, "Message sent successfully", StatusResponse.Type.SUCCESS, Objects.isNull(result) ? 0 : 1));
            }

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
            response.setStatus(new StatusResponse(1, "Messages fetched successfully", StatusResponse.Type.SUCCESS, messageEntries.size()));
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

}

