package com.dancestudio.erp.service;

import com.dancestudio.erp.response.PasswordResponse;

public interface PasswordService {

    PasswordResponse initiatePasswordReset(String email);

    PasswordResponse verifyOtp(String otpToken, String otp);
}
