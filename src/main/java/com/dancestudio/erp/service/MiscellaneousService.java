package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.PasswordEntry;
import com.dancestudio.erp.entry.UserEntry;
import com.dancestudio.erp.response.PasswordResponse;
import com.dancestudio.erp.response.ReportResponse;
import com.dancestudio.erp.response.StringResponse;
import com.dancestudio.erp.response.TemplateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface MiscellaneousService {

    ResponseEntity<StringResponse> uploadImage(String entityType, MultipartFile file);

    ResponseEntity<PasswordResponse> initiatePasswordReset(String email);

    ResponseEntity<PasswordResponse> verifyOtp(PasswordEntry passwordEntry);

    ResponseEntity<StringResponse> refreshToken(UserEntry userEntry);

    ResponseEntity<ReportResponse> getAnalysisReport(Long year, Long studioId);

    ResponseEntity<ReportResponse> getReports(String startDate, String endDate);

    ResponseEntity<TemplateResponse> getTemplates(Long studioId);

}
