package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.authentication.JwtUtil;
import com.dancestudio.erp.entry.PasswordEntry;
import com.dancestudio.erp.entry.UserEntry;
import com.dancestudio.erp.manager.UserManager;
import com.dancestudio.erp.manager.impl.PasswordManagerImpl;
import com.dancestudio.erp.response.PasswordResponse;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.response.StringResponse;
import com.dancestudio.erp.service.PasswordService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Setter(onMethod = @__({@Autowired}))
@Component
public class PasswordServiceImpl implements PasswordService {

    @Autowired
    private PasswordManagerImpl passwordManager;

    @Autowired
    private UserManager userManager;

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<PasswordResponse> initiatePasswordReset(String email) {
        PasswordResponse response = new PasswordResponse();
        try {
            PasswordEntry entry = passwordManager.initiatePasswordReset(email);
            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "OTP sent to your email.", StatusResponse.Type.SUCCESS));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(1, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<PasswordResponse> verifyOtp(PasswordEntry passwordEntry) {
        PasswordResponse response = new PasswordResponse();

        try {
            PasswordEntry entry = passwordManager.verifyOtp(passwordEntry);
            if (entry.isValid()) {
                UserEntry userEntry = userManager.getUserByEmail(passwordEntry.getUserEntry().getEmail());
                userEntry.setPassword(passwordEntry.getUserEntry().getPassword());
                userManager.update(userEntry.getUserId(), userEntry);
                response.setStatus(new StatusResponse(1, "OTP veriried, Password changed successfully", StatusResponse.Type.SUCCESS));
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response.setStatus(new StatusResponse(1, "Invalid or expired OTP", StatusResponse.Type.ERROR));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<StringResponse> refreshToken(UserEntry userEntry) {

        StringResponse response = new StringResponse();
        if (userEntry.getToken().startsWith("Bearer ")) {
            userEntry.setToken(userEntry.getToken().substring(7));
        }

        if (jwtUtil.validateRefreshToken(userEntry.getToken(), userEntry.getEmail())) {

            String newAccessToken = jwtUtil.generateAccessToken(userEntry.getEmail());
            response.setStatus(new StatusResponse(1, "Token fetched successfully", StatusResponse.Type.SUCCESS));
            response.setData(Collections.singletonList(newAccessToken));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        response.setStatus(new StatusResponse(1, "Invalid refresh token", StatusResponse.Type.ERROR));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
