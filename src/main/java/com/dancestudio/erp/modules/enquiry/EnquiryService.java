package com.dancestudio.erp.modules.enquiry;

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
public class EnquiryService extends BaseService<EnquiryEntry, Long> {

    private EnquiryManager enquiryManager;

    public ResponseEntity<BaseResponse<EnquiryEntry>> getAllEnquiries(Long branchId, Integer page, Integer size,
            Integer startMonth, Integer startYear,
            Integer endMonth, Integer endYear,
            String searchTerm) {
        BaseResponse<EnquiryEntry> response = new BaseResponse<EnquiryEntry>();

        try {
            Page<EnquiryEntry> entries = enquiryManager.getAllEnquiries(branchId, --page, size,
                    startMonth, startYear, endMonth, endYear, searchTerm);

            response.setData(entries.getContent());
            response.setStatus(new StatusResponse(1, "Enquiries retrieved successfully", StatusResponse.Type.SUCCESS,
                    entries.getTotalElements()));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    protected EnquiryEntry doAdd(EnquiryEntry entry) throws Exception {
        return enquiryManager.add(entry);
    }

    @Override
    protected EnquiryEntry doUpdate(Long id, EnquiryEntry entry) throws Exception {
        return enquiryManager.update(id, entry);
    }

    @Override
    protected void doDelete(Long id) throws Exception {
        enquiryManager.delete(id);
    }

    @Override
    protected EnquiryEntry doGet(Long id) throws Exception {
        return enquiryManager.getById(id);
    }
}
