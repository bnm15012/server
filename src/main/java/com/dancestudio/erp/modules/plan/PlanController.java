package com.dancestudio.erp.modules.plan;

import com.dancestudio.erp.base.BaseController;
import com.dancestudio.erp.base.BaseResponse;
import com.dancestudio.erp.base.BaseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plans")
public class PlanController extends BaseController<PlanEntry, Long> {

    @Autowired
    private PlanService planService;

    @GetMapping("/getAll")
    public ResponseEntity<BaseResponse<PlanEntry>> getAllPlans(HttpServletRequest request,
            @RequestParam(defaultValue = "false") Boolean AMC) {
        return planService.getAllPlans(request, AMC);
    }

    @Override
    protected BaseService<PlanEntry, Long> getService() {
        return planService;
    }
}
