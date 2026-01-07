package com.dancestudio.erp.modules.expense;

import com.dancestudio.erp.base.BaseResponse;
import com.dancestudio.erp.base.BaseService;
import com.dancestudio.erp.response.StatusResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Setter(onMethod = @__({ @Autowired }))
@Component
public class ExpenseService extends BaseService<ExpenseEntry, Long>  {

    @Autowired
    private ExpenseManager manager;

    @Override
    protected ExpenseEntry doAdd(ExpenseEntry entry) throws Exception {
        return manager.add(entry);
    }

    @Override
    protected ExpenseEntry doUpdate(Long id, ExpenseEntry entry) throws Exception {
        return manager.update(id, entry);
    }

    @Override
    protected void doDelete(Long id) throws Exception {
        manager.delete(id);
    }

    @Override
    protected ExpenseEntry doGet(Long id) throws Exception {
        return manager.getById(id);
    }


    public ResponseEntity<BaseResponse<ExpenseEntry>> getAllExpenses(Long branchId, Integer page, Integer size,
            Integer startDate, Integer startMonth, Integer startYear,
            Integer endDate, Integer endMonth, Integer endYear, String searchTerm) {
        BaseResponse<ExpenseEntry> response = new BaseResponse<>();

        try {
            Page<ExpenseEntry> entries = manager.getAllExpenses(branchId, --page, size, startDate, startMonth,
                    startYear, endDate,
                    endMonth, endYear, searchTerm);

            response.setData(entries.getContent());

            response.setStatus(new StatusResponse(1, "Expenses retrieved successfully", StatusResponse.Type.SUCCESS,
                    (int) entries.getTotalElements()));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
