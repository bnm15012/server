package com.dancestudio.erp.controller;

import com.dancestudio.erp.entry.UserEntry;
import com.dancestudio.erp.response.PasswordResponse;
import com.dancestudio.erp.response.ReportResponse;
import com.dancestudio.erp.response.StringResponse;
import com.dancestudio.erp.service.ImageService;
import com.dancestudio.erp.service.PasswordService;
import com.dancestudio.erp.service.ReportService;
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
    public PasswordResponse requestPasswordReset(@RequestParam String email) {
        return passwordService.initiatePasswordReset(email);
    }

    @PostMapping("password/verify")
    public PasswordResponse verifyOtp(@RequestParam String otpToken, @RequestParam String otp) {
        return passwordService.verifyOtp(otpToken, otp);
    }

    @PostMapping("password/refreshToken")
    public StringResponse refreshToken(@RequestBody UserEntry userEntry) {
        return passwordService.refreshToken(userEntry);
    }

}
