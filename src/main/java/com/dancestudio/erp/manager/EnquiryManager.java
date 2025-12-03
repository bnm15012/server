package com.dancestudio.erp.manager;

import java.util.List;

import com.dancestudio.erp.entry.EnquiryEntry;

public interface EnquiryManager extends BaseManagerInt<EnquiryEntry, Long> {

    List<EnquiryEntry> getAllEnquiries(Long branchId, Integer page, Integer size,
            Integer startMonth, Integer startYear,
            Integer endMonth, Integer endYear,
            String searchTerm) throws Exception;

    long countEnquiriesByBranchId(Long branchId) throws Exception;

    long countEnquiriesByBranchIdAndMonth(Long branchId, Integer startMonth, Integer startYear,
            Integer endMonth, Integer endYear, String searchTerm) throws Exception;
}
