package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.entry.ReportEntry;
import com.dancestudio.erp.manager.ReportManager;
import com.dancestudio.erp.response.ReportResponse;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.service.ReportService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Setter(onMethod = @__({@Autowired}))
@Component
public class ReportServiceImpl implements ReportService {

    private ReportManager reportManager;

    @Override
    public ReportResponse generateIncomeReport(Long year) {

        ReportResponse response = new ReportResponse();

        List<ReportEntry> reportEntries = reportManager.generateIncomeReport(year);
        response.setData(reportEntries);
        response.setStatus(new StatusResponse(1, StatusResponse.Type.SUCCESS, Objects.isNull(reportEntries) ? 0 : reportEntries.size()));

        return response;
    }
}
