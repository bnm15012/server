package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entry.MonthlyReportEntry;
import com.dancestudio.erp.entry.ReportEntry;
import com.dancestudio.erp.manager.PaymentManager;
import com.dancestudio.erp.manager.ReportManager;
import com.dancestudio.erp.manager.StudentActivityAssignmentManager;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Setter(onMethod = @__({@Autowired}))
@Component
public class ReportManagerImpl implements ReportManager {

    @Autowired
    private PaymentManager paymentManager;

    @Autowired
    private StudentActivityAssignmentManager studentActivityAssignmentManager;


    public List<MonthlyReportEntry> generateSalesReport(Long year, Long studioId) {
        return studentActivityAssignmentManager.calculateSalesReport(year, studioId);
    }
}
