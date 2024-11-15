package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.PasswordEntry;
import com.dancestudio.erp.entry.UserEntry;
import com.dancestudio.erp.response.PasswordResponse;
import com.dancestudio.erp.response.ReportResponse;
import com.dancestudio.erp.response.StringResponse;
import com.dancestudio.erp.service.ImageService;
import com.dancestudio.erp.service.PasswordService;
import com.dancestudio.erp.service.ReportService;
import com.dancestudio.erp.service.WhatsAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/")
public class MiscellaneousController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private WhatsAppService whatsappService;

    @PostMapping("uploadImage/{entityType}")
    public ResponseEntity<StringResponse> uploadImage(@PathVariable("entityType") String entityType,
                                                      @RequestParam("file") MultipartFile file) {
        return imageService.uploadImage(entityType, file);
    }

    @GetMapping("reports/{year}")
    public ResponseEntity<ReportResponse> generateIncomeReport(@PathVariable Long year) {
        return reportService.generateIncomeReport(year);
    }

    @PostMapping("password/reset")
    public ResponseEntity<PasswordResponse> requestPasswordReset(@RequestParam String email) {
        return passwordService.initiatePasswordReset(email);
    }

    @PostMapping("password/verify")
    public ResponseEntity<PasswordResponse> verifyOtp(@RequestBody PasswordEntry passwordEntry) {
        return passwordService.verifyOtp(passwordEntry);
    }

    @PostMapping("password/refreshToken")
    public ResponseEntity<StringResponse> refreshToken(@RequestBody UserEntry userEntry) {
        return passwordService.refreshToken(userEntry);
    }

    @PostMapping("/whatsapp/sendMessage")
    public String sendWhatsAppMessage(@RequestParam String to, @RequestParam String message) {
        whatsappService.sendWhatsAppMessage(to, message);
        return "Message sent successfully!";
    }

}
