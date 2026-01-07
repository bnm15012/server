package com.dancestudio.erp.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.dancestudio.erp.enums.ReportType;
import com.dancestudio.erp.response.ReportResponse;
import com.dancestudio.erp.service.ReportService;
import com.dancestudio.erp.util.DateUtil;

import lombok.Setter;

@Component
@Setter(onMethod = @__({ @Autowired }))
public class ReportServiceImpl implements ReportService {

    @Override
    public ResponseEntity<ReportResponse<?>> getReport(Long studioId, Long branchId, ReportType reportType,
            String startDate, String endDate, Integer page, Integer size) {
        // Pageable pageable = size == -1 ? Pageable.unpaged() : PageRequest.of(page, size, Sort.by(Sort.Direction.DESC));
        Map<String, Date> dateRange = DateUtil.getUTCDateRange(startDate, endDate);

        switch (reportType) {
            case PAYMENT:

                break;

            default:
                break;
        }
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getReport'");
    }

}
