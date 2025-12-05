package com.dancestudio.erp.modules.payments;

import com.dancestudio.erp.base.BaseResponse;
import com.dancestudio.erp.base.BaseService;
import com.dancestudio.erp.modules.payments.entry.PaymentEntry;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.util.DateUtil;

import lombok.Setter;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Setter(onMethod = @__({ @Autowired }))
@Component
public class PaymentService extends BaseService<PaymentEntry, Long> {

    private PaymentManager paymentManager;

    @Override
    protected PaymentEntry doAdd(PaymentEntry entry) throws Exception {
        throw new UnsupportedOperationException("Not supported to ADD");
    }

    @Override
    protected PaymentEntry doUpdate(Long id, PaymentEntry entry) throws Exception {
        return paymentManager.update(id, entry);
    }

    @Override
    protected void doDelete(Long id) throws Exception {
        throw new UnsupportedOperationException("Not supported to delete");
    }

    @Override
    protected PaymentEntry doGet(Long id) throws Exception {
        throw new UnsupportedOperationException("Not supported to get by one");
    }

    public ResponseEntity<BaseResponse<PaymentEntry>> getAll(Long branchId, Integer page, Integer size,
            String startDate, String endDate) {
        BaseResponse<PaymentEntry> response = new BaseResponse<>();
        Page<PaymentEntry> entries;
        try {
            if (Objects.nonNull(startDate) && Objects.nonNull(endDate)) {
                Map<String, Date> dateRange = DateUtil.getUTCDateRange(startDate, endDate);
                entries = paymentManager.getAll(branchId, --page, size, dateRange.get("start"),
                        dateRange.get("end"));
            } else {
                entries = paymentManager.getAll(branchId, --page, size, null, null);
            }
            response.setData(entries.getContent());
            response.setStatus(new StatusResponse(1, "Payments retrieved successfully", StatusResponse.Type.SUCCESS,
                    (int) entries.getTotalElements()));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
