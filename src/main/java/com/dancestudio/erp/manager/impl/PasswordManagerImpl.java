package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.authentication.JwtOtpUtil;
import com.dancestudio.erp.entry.PasswordEntry;
import com.dancestudio.erp.manager.EmailManager;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Setter(onMethod = @__({@Autowired}))
@Component
public class PasswordManagerImpl {

    @Autowired
    private JwtOtpUtil jwtOtpUtil;

    @Autowired
    private EmailManager emailManager;

    private static final String PASSWORD_CHANGE_NOTIFICATION = "Password Reset OTP";

    public PasswordEntry initiatePasswordReset(String email) {
        String otp = jwtOtpUtil.generateOtp();
        String otpToken = jwtOtpUtil.generateOtpToken(email, otp);
        String body = "Your OTP is: " + otp + ". It is valid for 5 minutes.";
        emailManager.sendEmail(email, PASSWORD_CHANGE_NOTIFICATION, body);

        PasswordEntry passwordEntry = new PasswordEntry();
        passwordEntry.setOtpToken(otpToken);
        passwordEntry.setTokenValidity(300000L);

        return passwordEntry;
    }

    public PasswordEntry verifyOtp(String otpToken, String otp) {
        boolean isValid =  jwtOtpUtil.validateOtpToken(otpToken, otp);
        PasswordEntry passwordEntry = new PasswordEntry();
        passwordEntry.setOtpToken(otpToken);
        passwordEntry.setTokenValidity(300000L);
        passwordEntry.setValid(isValid);

        return passwordEntry;
    }
}
