package com.dancestudio.erp.controller;

import com.dancestudio.erp.service.WhatsAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/whatsapp")
public class WhatsappController {

    @Autowired
    private WhatsAppService whatsappService;

    @PostMapping("/send")
    public String sendWhatsAppMessage(@RequestParam String to, @RequestParam String message) {
        whatsappService.sendWhatsAppMessage(to, message);
        return "Message sent successfully!";
    }
}
