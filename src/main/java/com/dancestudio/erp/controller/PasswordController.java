package com.dancestudio.erp.controller;

import com.dancestudio.erp.response.PasswordResponse;
import com.dancestudio.erp.response.StringResponse;
import com.dancestudio.erp.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password")
public class PasswordController {

    @Autowired
    private PasswordService passwordService;

    @PostMapping("/reset")
    public PasswordResponse requestPasswordReset(@RequestParam String email) {
        return passwordService.initiatePasswordReset(email);
    }

    @PostMapping("/verify")
    public PasswordResponse verifyOtp(@RequestParam String otpToken, @RequestParam String otp) {
        return passwordService.verifyOtp(otpToken, otp);
    }

    @GetMapping("/refreshToken/{refreshToken}")
    public StringResponse refreshToken(@PathVariable String refreshToken, @RequestHeader String email) {
        return passwordService.refreshToken(refreshToken, email);
    }

}
