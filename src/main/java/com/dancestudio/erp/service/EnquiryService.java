package com.dancestudio.erp.service;

import com.dancestudio.erp.entry.EnquiryEntry;
import com.dancestudio.erp.response.EnquiryResponse;
import org.springframework.http.ResponseEntity;

public interface EnquiryService extends BaseService<EnquiryEntry, EnquiryResponse, Long> {

    ResponseEntity<EnquiryResponse> getAllEnquiries(Long branchId, Integer page, Integer size, Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, String searchTerm);

}
