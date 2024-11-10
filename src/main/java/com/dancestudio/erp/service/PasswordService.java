package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.PasswordEntry;
import com.dancestudio.erp.entry.UserEntry;
import com.dancestudio.erp.response.PasswordResponse;
import com.dancestudio.erp.response.StringResponse;
import org.springframework.http.ResponseEntity;

public interface PasswordService {

    ResponseEntity<PasswordResponse> initiatePasswordReset(String email);

    ResponseEntity<PasswordResponse> verifyOtp(PasswordEntry passwordEntry);

    ResponseEntity<StringResponse> refreshToken(UserEntry userEntry);

}
