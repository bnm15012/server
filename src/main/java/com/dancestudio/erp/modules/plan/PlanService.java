package com.dancestudio.erp.modules.plan;

import com.dancestudio.erp.base.BaseResponse;
import com.dancestudio.erp.base.BaseService;
import com.dancestudio.erp.response.StatusResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter(onMethod = @__({ @Autowired }))
@Component
public class PlanService extends BaseService<PlanEntry, Long> {

    private PlanManager planManager;

    @Override
    protected PlanEntry doAdd(PlanEntry entry) throws Exception {
        return planManager.add(entry);
    }

    @Override
    protected PlanEntry doUpdate(Long id, PlanEntry entry) throws Exception {
        return planManager.update(id, entry);
    }

    @Override
    protected void doDelete(Long id) throws Exception {
        planManager.delete(id);
    }

    @Override
    protected PlanEntry doGet(Long id) throws Exception {
        return planManager.getById(id);
    }

    public ResponseEntity<BaseResponse<PlanEntry>> getAllPlans(HttpServletRequest request, Boolean AMC) {
        BaseResponse<PlanEntry> response = new BaseResponse<PlanEntry>();

        try {
            List<PlanEntry> entries = planManager.getAllPlans(request, AMC);
            response.setData(entries);
            response.setStatus(new StatusResponse(1, "Plans retrieved successfully", StatusResponse.Type.SUCCESS,
                    (int) entries.size()));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
