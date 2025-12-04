package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.authentication.JwtUtil;
import com.dancestudio.erp.entry.PasswordEntry;
import com.dancestudio.erp.entry.UserEntry;
import com.dancestudio.erp.enums.AuthType;
import com.dancestudio.erp.manager.UserManager;
import com.dancestudio.erp.modules.message_queue.services.EmailService;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Setter(onMethod = @__({@Autowired}))
@Component
public class PasswordManagerImpl {

    private final EmailService emailService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserManager userManager;

    private static final String PASSWORD_CHANGE_NOTIFICATION = "Password Reset OTP";

    PasswordManagerImpl(EmailService emailService) {
        this.emailService = emailService;
    }

    public PasswordEntry initiatePasswordReset(String email) throws Exception {
        UserEntry userEntry = userManager.getUserByEmail(email);
        if(Objects.isNull(userEntry)) {
            throw new Exception("This is not a valid user");
        }

        String otp = jwtUtil.generateOtp();
        String otpToken = jwtUtil.generateToken(email, null, otp, AuthType.OTP);
        String body = "Your OTP is: " + otp + ". It is valid for 5 minutes.";
        emailService.sendHighPriorityEmail(email, PASSWORD_CHANGE_NOTIFICATION, body, null, null, null, null);

        PasswordEntry passwordEntry = new PasswordEntry();
        passwordEntry.setOtpToken(otpToken);
        passwordEntry.setTokenValidity(300000L);

        return passwordEntry;
    }

    public PasswordEntry verifyOtp(PasswordEntry passwordEntry) {
        boolean isValid =  jwtUtil.validateOtpToken(passwordEntry.getOtpToken(), passwordEntry.getOtp());
        PasswordEntry entry = new PasswordEntry();
        entry.setOtpToken(passwordEntry.getOtpToken());
        entry.setTokenValidity(300000L);
        entry.setValid(isValid);

        return entry;
    }

    public static String generateRandomPassword() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }
}
