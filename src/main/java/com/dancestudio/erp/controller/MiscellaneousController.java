package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.PasswordEntry;
import com.dancestudio.erp.entry.SendMessageRequestEntry;
import com.dancestudio.erp.entry.UserEntry;
import com.dancestudio.erp.response.*;
import com.dancestudio.erp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/")
public class MiscellaneousController {

    @Autowired
    private MiscellaneousService miscellaneousService;

    @Autowired
    private MessageService whatsappService;

    @Autowired
    private MessageService messageService;

    @PostMapping("uploadImage/{entityType}")
    public ResponseEntity<StringResponse> uploadImage(@PathVariable("entityType") String entityType, @RequestParam("file") MultipartFile file) {
        return miscellaneousService.uploadImage(entityType, file);
    }

    @GetMapping("analysis/{year}/{studioId}")
    public ResponseEntity<ReportResponse> getAnalysisReport(@PathVariable Long year, @PathVariable Long studioId) {
        return miscellaneousService.getAnalysisReport(year, studioId);
    }

    @GetMapping("reports/{studioId}/{branchId}")
    public ResponseEntity<IEReportResponse> getReports(@PathVariable("studioId") Long studioId, @PathVariable("branchId") Long branchId,
                                                       @RequestParam("startMonth") Long startMonth, @RequestParam("startYear") Long startYear,
                                                       @RequestParam("endMonth") Long endMonth, @RequestParam("endYear") Long endYear) {
        return miscellaneousService.getReports(studioId, branchId, startMonth, startYear, endMonth, endYear);
    }
    
    @GetMapping("reports/payments/{studioId}/{branchId}")
    public ResponseEntity<PaymentResponse> getPaymentReports(@PathVariable("studioId") Long studioId, @PathVariable("branchId") Long branchId,
                                                       @RequestParam("startMonth") int startMonth, @RequestParam("startYear") int startYear,
                                                       @RequestParam("endMonth") int endMonth, @RequestParam("endYear") int endYear, 
                                                       @RequestParam("status") String status) {
        return miscellaneousService.getPaymentReports(studioId, branchId, startMonth, startYear, endMonth, endYear, status);
    }

    @PostMapping("password/reset")
    public ResponseEntity<PasswordResponse> requestPasswordReset(@RequestParam String email) {
        return miscellaneousService.initiatePasswordReset(email);
    }

    @PostMapping("password/verify")
    public ResponseEntity<PasswordResponse> verifyOtp(@RequestBody PasswordEntry passwordEntry) {
        return miscellaneousService.verifyOtp(passwordEntry);
    }

    @PostMapping("password/refreshToken")
    public ResponseEntity<StringResponse> refreshToken(@RequestBody UserEntry userEntry) {
        return miscellaneousService.refreshToken(userEntry);
    }

    @GetMapping("getTemplates/{studioId}")
    public ResponseEntity<TemplateResponse> getTemplates(@PathVariable("studioId") Long studioId) {
        return miscellaneousService.getTemplates(studioId);
    }

    @PostMapping("whatsapp/sendMessage")
    public String sendWhatsAppMessage(@RequestParam String to, @RequestParam String message) {
        whatsappService.sendWhatsAppMessage(to, message);
        return "Message sent successfully!";
    }

    @PostMapping("sendMessage")
    public ResponseEntity<SendMessageResponse> sendMessage(@RequestBody SendMessageRequestEntry request) {
        return messageService.sendMessage(request);
    }

}
