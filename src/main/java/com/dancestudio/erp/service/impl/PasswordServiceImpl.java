package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.authentication.JwtUtil;
import com.dancestudio.erp.entry.PasswordEntry;
import com.dancestudio.erp.manager.impl.PasswordManagerImpl;
import com.dancestudio.erp.response.PasswordResponse;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.response.StringResponse;
import com.dancestudio.erp.service.PasswordService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Setter(onMethod = @__({@Autowired}))
@Component
public class PasswordServiceImpl implements PasswordService {

    @Autowired
    private PasswordManagerImpl passwordManager;

    @Autowired
    private JwtUtil jwtUtil;

    public PasswordResponse initiatePasswordReset(String email) {
        PasswordResponse response = new PasswordResponse();
        try {
            PasswordEntry entry = passwordManager.initiatePasswordReset(email);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "OTP sent to your email.", StatusResponse.Type.SUCCESS));
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(1, ex.getMessage(), StatusResponse.Type.ERROR));
        }
        return response;
    }

    public PasswordResponse verifyOtp(String otpToken, String otp) {
        PasswordResponse response = new PasswordResponse();

        PasswordEntry entry = passwordManager.verifyOtp(otpToken, otp);
        if (entry.isValid()) {
            response.setStatus(new StatusResponse(1, "OTP verified. Proceed with password reset", StatusResponse.Type.SUCCESS));
        } else {
            response.setStatus(new StatusResponse(1, "Invalid or expired OTP", StatusResponse.Type.ERROR));
        }
        return response;
    }

    public StringResponse refreshToken(String refreshToken, String email) {

        StringResponse response = new StringResponse();
        if (refreshToken.startsWith("Bearer ")) {
            refreshToken = refreshToken.substring(7);
        }

        if (jwtUtil.validateRefreshToken(refreshToken, email)) {

            String newAccessToken = jwtUtil.generateAccessToken(email);
            response.setStatus(new StatusResponse(1, "Token fetched successfully", StatusResponse.Type.SUCCESS));
            response.setData(Collections.singletonList(newAccessToken));
            return response;
        }

        response.setStatus(new StatusResponse(1, "Invalid refresh token", StatusResponse.Type.ERROR));
        return response;
    }
}
