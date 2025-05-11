package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.PasswordEntry;
import com.dancestudio.erp.entry.SendMessageRequestEntry;
import com.dancestudio.erp.entry.StringRequest;
import com.dancestudio.erp.entry.UserEntry;
import com.dancestudio.erp.response.*;
import com.dancestudio.erp.service.MessageService;
import com.dancestudio.erp.service.MiscellaneousService;
import com.dancestudio.erp.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/")
public class MiscellaneousController {

    @Autowired private MiscellaneousService miscellaneousService;
    @Autowired private MessageService messageService;
    @Autowired private S3Service s3Service;

    @PostMapping("uploadImage/{entityType}")
    public ResponseEntity<StringResponse> uploadImage(@PathVariable("entityType") String entityType, @RequestParam("file") MultipartFile file) {
        return miscellaneousService.uploadImage(entityType, file);
    }

    @GetMapping("analysis/{year}/{studioId}")
    public ResponseEntity<ReportResponse> getAnalysisReport(@PathVariable Integer year, @PathVariable Long studioId) {
        return miscellaneousService.getAnalysisReport(year, studioId);
    }

    @GetMapping("reports/{studioId}/{branchId}/{startMonth}/{startYear}/{endMonth}/{endYear}")
    public ResponseEntity<IEReportResponse> getReports(@PathVariable Long studioId,
            @PathVariable Long branchId,
            @PathVariable Integer startMonth,
            @PathVariable Integer startYear,
            @PathVariable Integer endMonth,
            @PathVariable Integer endYear) {
        return miscellaneousService.getReports(studioId, branchId, startMonth, startYear, endMonth, endYear);
    }

    @GetMapping("reports/payments/{studioId}/{branchId}/{startMonth}/{startYear}/{endMonth}/{endYear}")
    public ResponseEntity<PaymentResponse> getPaymentReports(@PathVariable Long studioId,
            @PathVariable Long branchId,
            @PathVariable Integer startMonth,
            @PathVariable Integer startYear,
            @PathVariable Integer endMonth,
            @PathVariable Integer endYear,
            @RequestParam String status) {
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
        messageService.sendWhatsAppMessage(to, message);
        return "Message sent successfully!";
    }

    @PostMapping("sendMessage")
    public ResponseEntity<SendMessageResponse> sendMessage(@RequestBody SendMessageRequestEntry request) {
        return messageService.sendMessage(request);
    }

    @GetMapping("getMessageHistory/{branchId}")
    public ResponseEntity<MessageResponse> getMessageHistory(@PathVariable Long branchId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "-1") int size) {
        return messageService.getMessagesByBranchId(branchId, page, size);
    }

    @GetMapping("getMessageRecipients/{messageId}")
    public ResponseEntity<MessageRecipientResponse> getMessageRecipients(@PathVariable Long messageId) {
        return messageService.getMessageRecipients(messageId);
    }

    @PostMapping("/generatePresignUrl")
    public ResponseEntity<PreSignedResponse> getPresignedUrl(@RequestBody StringRequest request) {
        return s3Service.generatePresignedUrl(request);
    }
}
