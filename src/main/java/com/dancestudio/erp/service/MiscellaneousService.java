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

    ResponseEntity<IEPReportResponse> getAnalysisReport(Integer year, Long studioId);

    ResponseEntity<IEReportResponse> getExpenseIncomeReports(Long studioId, Long branchId, Integer startDate,
            Integer startMonth, Integer startYear, Integer endDate, Integer endMonth, Integer endYear,
            String paymentType);

    ResponseEntity<PaymentResponse> getPaymentReports(Long studioId, Long branchId, Integer startDate, int startMonth,
            int startYear, Integer endDate, int endMonth, int endYear, String status, String paymentType);
}
