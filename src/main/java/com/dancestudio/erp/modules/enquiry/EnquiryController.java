package com.dancestudio.erp.modules.enquiry;
import com.dancestudio.erp.base.BaseController;
import com.dancestudio.erp.base.BaseResponse;
import com.dancestudio.erp.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enquiries")
public class EnquiryController extends BaseController<EnquiryEntry, Long> {

    @Autowired
    private EnquiryService enquiryService;

    @GetMapping("/getAll/{branchId}")
    public ResponseEntity<BaseResponse<EnquiryEntry>> getAllEnquiries(@PathVariable Long branchId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Integer startMonth,
            @RequestParam(required = false) Integer startYear,
            @RequestParam(required = false) Integer endMonth,
            @RequestParam(required = false) Integer endYear,
            @RequestParam(required = false) String searchTerm) {
        return enquiryService.getAllEnquiries(branchId, page, size,
                startMonth, startYear, endMonth, endYear, searchTerm);
    }

    @Override
    protected BaseService<EnquiryEntry, Long> getService() {
        return enquiryService;
    }
}
