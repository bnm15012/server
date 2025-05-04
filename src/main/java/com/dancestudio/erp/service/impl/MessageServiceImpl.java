package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.entry.SendMessageRequestEntry;
import com.dancestudio.erp.manager.MessageManager;
import com.dancestudio.erp.response.SendMessageResponse;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.service.MessageService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Setter
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageManager messageManager;


    @Override
    public void sendWhatsAppMessage(String to, String messageText) {
    }

    @Override
    public ResponseEntity<SendMessageResponse> sendMessage(SendMessageRequestEntry request) {

        SendMessageResponse response = new SendMessageResponse();
        try {
            SendMessageResponse result = messageManager.sendMessage(request);
            response.setStatus(new StatusResponse(1, StatusResponse.Type.SUCCESS, Objects.isNull(result) ? 0 : 1));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }


    }
}

