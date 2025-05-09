package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.PasswordEntry;
import com.dancestudio.erp.entry.UserEntry;
import com.dancestudio.erp.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface MiscellaneousService {

    ResponseEntity<StringResponse> uploadImage(String entityType, MultipartFile file);

    ResponseEntity<PasswordResponse> initiatePasswordReset(String email);

    ResponseEntity<PasswordResponse> verifyOtp(PasswordEntry passwordEntry);

    ResponseEntity<StringResponse> refreshToken(UserEntry userEntry);

    ResponseEntity<ReportResponse> getAnalysisReport(Integer year, Long studioId);

    ResponseEntity<IEReportResponse> getReports(Long studioId, Long branchId, Integer startMonth, Integer startYear, Integer endMonth, Integer endYear);

    ResponseEntity<PaymentResponse> getPaymentReports(Long studioId, Long branchId, int startMonth, int startYear, int endMonth, int endYear, String status);

    ResponseEntity<TemplateResponse> getTemplates(Long studioId);

}
