package com.dancestudio.erp.service;

import com.dancestudio.erp.response.PasswordResponse;
import com.dancestudio.erp.response.StringResponse;

public interface PasswordService {

    PasswordResponse initiatePasswordReset(String email);

    PasswordResponse verifyOtp(String otpToken, String otp);

    StringResponse refreshToken(String refreshToken, String email);

}
