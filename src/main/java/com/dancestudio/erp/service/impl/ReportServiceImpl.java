package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.entry.MonthlyReportEntry;
import com.dancestudio.erp.entry.ReportEntry;
import com.dancestudio.erp.manager.ReportManager;
import com.dancestudio.erp.response.ReportResponse;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.service.ReportService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Setter(onMethod = @__({@Autowired}))
@Component
public class ReportServiceImpl implements ReportService {

    private ReportManager reportManager;

    @Override
    public ResponseEntity<ReportResponse> generateSalesReport(Long year, Long studioId) {
        ReportResponse response = new ReportResponse();
        try {
            List<MonthlyReportEntry> reportEntries = reportManager.generateSalesReport(year, studioId);
            response.setData(reportEntries);
            response.setStatus(new StatusResponse(1, StatusResponse.Type.SUCCESS, Objects.isNull(reportEntries) ? 0 : reportEntries.size()));

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(0, ex.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
