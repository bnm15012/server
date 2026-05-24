package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.authentication.JwtUtil;
import com.dancestudio.erp.entry.*;
import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.manager.ImageManager;
import com.dancestudio.erp.manager.ReportManager;

import com.dancestudio.erp.manager.UserManager;
import com.dancestudio.erp.manager.impl.PasswordManagerImpl;
import com.dancestudio.erp.modules.payments.PaymentManager;
import com.dancestudio.erp.modules.payments.entry.PaymentEntry;
import com.dancestudio.erp.response.*;
import com.dancestudio.erp.service.MiscellaneousService;
import com.dancestudio.erp.util.DateUtil;

import lombok.Setter;

import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@Setter(onMethod = @__({ @Autowired }))
public class MiscellaneousServiceImpl implements MiscellaneousService {

    private ImageManager imageManager;
    private PasswordManagerImpl passwordManager;
    private PaymentManager paymentManager;
    private UserManager userManager;
    private JwtUtil jwtUtil;
    private ReportManager reportManager;

    @Override
    public ResponseEntity<StringResponse> uploadImage(String entityType, MultipartFile file) {
        StringResponse response = new StringResponse();

        try {
            String url = imageManager.uploadImage(entityType, file);
            response.setData(Collections.singletonList(url));
            response.setStatus(new StatusResponse(1, "Image uploaded successfully", StatusResponse.Type.SUCCESS));
            return ResponseEntity.ok(response);
        } catch (SizeLimitExceededException ex) {
            response.setStatus(
                    new StatusResponse(1, "Image size is too large. Upload max 1MB file.", StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(1, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

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
                response.setStatus(new StatusResponse(1, "OTP veriried, Password changed successfully",
                        StatusResponse.Type.SUCCESS));
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

    @Override
    public ResponseEntity<IEPReportResponse> getAnalysisReport(Integer year, Long branchId) {
        IEPReportResponse response = new IEPReportResponse();
        try {
            List<MonthlyReportEntry> reportEntries = reportManager.getAnalysisReport(year, branchId);
            response.setData(reportEntries);
            response.setStatus(new StatusResponse(1, StatusResponse.Type.SUCCESS,
                    Objects.isNull(reportEntries) ? 0 : reportEntries.size()));

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<IEReportResponse> getExpenseIncomeReports(Long studioId, Long branchId, Integer startDate,
            Integer startMonth, Integer startYear, Integer endDate, Integer endMonth, Integer endYear) {

        IEReportResponse response = new IEReportResponse();
        try {
            IEReportEntry reportEntry = reportManager.getReports(studioId, branchId, startDate, startMonth, startYear,
                    endDate, endMonth, endYear);
            response.setData(Collections.singletonList(reportEntry));
            response.setStatus(new StatusResponse(1, StatusResponse.Type.SUCCESS, 1));

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<PaymentResponse> getPaymentReports(Long studioId, Long branchId,
            Integer startDate, int startMonth, int startYear,
            Integer endDate, int endMonth, int endYear,
            String status) {
        PaymentResponse response = new PaymentResponse();
        try {
            Map<String, Date> utcDateRange = DateUtil.getUTCDateRange(startDate,
                    startMonth, startYear, endDate, endMonth, endYear);

            List<PaymentEntry> paymentEntries = paymentManager.getAll(branchId, 0, -1,
                    utcDateRange.get("start"),
                    utcDateRange.get("end"), PaymentStatus.valueOf(status)).getContent();
            response.setData((paymentEntries));
            response.setStatus(
                    new StatusResponse(1, "Report data retrieved successfully", StatusResponse.Type.SUCCESS, 1));

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
