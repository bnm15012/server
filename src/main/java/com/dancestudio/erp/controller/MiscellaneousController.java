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

    @Autowired
    private MiscellaneousService miscellaneousService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private S3Service s3Service;

    @PostMapping("uploadImage/{entityType}")
    public ResponseEntity<StringResponse> uploadImage(@PathVariable("entityType") String entityType, @RequestParam("file") MultipartFile file) {
        return miscellaneousService.uploadImage(entityType, file);
    }

    @GetMapping("analysis/{year}/{studioId}")
    public ResponseEntity<IEPReportResponse> getAnalysisReport(@PathVariable Integer year, @PathVariable Long studioId) {
        return miscellaneousService.getAnalysisReport(year, studioId);
    }

    @GetMapping("reports/{studioId}/{branchId}/{startDate}/{startMonth}/{startYear}/{endDate}/{endMonth}/{endYear}")
    public ResponseEntity<IEReportResponse> getReports(@PathVariable Long studioId,
            @PathVariable Long branchId,
            @PathVariable Integer startDate,
            @PathVariable Integer startMonth,
            @PathVariable Integer startYear,
            @PathVariable Integer endDate,
            @PathVariable Integer endMonth,
            @PathVariable Integer endYear) {
        return miscellaneousService.getExpenseIncomeReports(studioId, branchId, startDate, startMonth, startYear, endDate, endMonth, endYear);
    }

    @GetMapping("reports/payments/{studioId}/{branchId}/{startDate}/{startMonth}/{startYear}/{endDate}/{endMonth}/{endYear}")
    public ResponseEntity<PaymentResponse> getPaymentReports(@PathVariable Long studioId,
            @PathVariable Long branchId,
            @PathVariable Integer startDate,
            @PathVariable Integer startMonth,
            @PathVariable Integer startYear,
            @PathVariable Integer endDate,
            @PathVariable Integer endMonth,
            @PathVariable Integer endYear,
            @RequestParam String status) {
        return miscellaneousService.getPaymentReports(studioId, branchId, startDate, startMonth, startYear, endDate, endMonth, endYear, status);
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

    // TODO: remove this endpoint after testing
    @GetMapping("getTemplates/{studioId}")
    public ResponseEntity<TemplateResponse> getTemplates(@PathVariable("studioId") Long studioId) {
        return miscellaneousService.getTemplates(studioId);
    }

    @PostMapping("whatsapp/sendMessage")
    public String sendWhatsAppMessage(@RequestParam String to, @RequestParam String message) {
        messageService.sendWhatsAppMessage(to, message);
        return "Message sent successfully!";
    }

    // @PostMapping("sendMessage")
    // public ResponseEntity<SendMessageResponse> sendMessage(@RequestBody SendMessageRequestEntry request) {
    //     return messageService.sendMessage(request);
    // }

    @PostMapping("sendMessage/{branchId}")
    public ResponseEntity<SendMessageResponse> sendMessage(@ModelAttribute SendMessageRequestEntry request, @PathVariable String branchId,
            @RequestParam(value = "file", required=false) MultipartFile file, 
            @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "-1") int size) {
        return messageService.sendMessage(request, file, page, size);
    }

    @GetMapping("getMessageHistory/{branchId}")
    public ResponseEntity<MessageResponse> getMessageHistory(@PathVariable Long branchId,
            @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "-1") int size) {
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

    @GetMapping("/whatsapp/createSession/{branchId}")
    public ResponseEntity<CreateSessionResponse> createSession(@PathVariable Long branchId) {
        return messageService.createSession(branchId);
    }

    @GetMapping("/whatsapp/status/{branchId}")
    public ResponseEntity<WhatsAppStatusResponse> checkSessionStatus(@PathVariable Long branchId) {
        return messageService.checkStatus(branchId);
    }

    @GetMapping("/whatsapp/logout/{branchId}")
    public ResponseEntity<WhatsAppStatusResponse> logoutWhatsAppSession(@PathVariable Long branchId) {
        return messageService.logoutWhatsAppSession(branchId);
    }
}
