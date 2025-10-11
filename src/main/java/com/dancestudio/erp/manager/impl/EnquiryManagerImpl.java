package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.converter.EnquiryConverter;
import com.dancestudio.erp.entity.Enquiry;
import com.dancestudio.erp.entry.EnquiryEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.EnquiryManager;
import com.dancestudio.erp.repository.EnquiryRepository;
import com.dancestudio.erp.specification.EnquirySpecifications;
import com.dancestudio.erp.util.DateUtil;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Setter(onMethod = @__({ @Autowired }))
@Component
public class EnquiryManagerImpl implements EnquiryManager {

    private EnquiryRepository enquiryRepository;

    @Override
    public EnquiryEntry add(EnquiryEntry entry) throws Exception {
        Enquiry entity = EnquiryConverter.toEntity(entry, null);
        return EnquiryConverter.toEntry(enquiryRepository.save(entity));
    }

    @Override
    public EnquiryEntry update(Long id, EnquiryEntry entry) throws Exception {
        Enquiry existing = enquiryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Enquiry not found with id: " + id));

        Enquiry updated = EnquiryConverter.toEntity(entry, existing);
        return EnquiryConverter.toEntry(enquiryRepository.save(updated));
    }

    @Override
    public void delete(Long id) throws Exception {
        if (!enquiryRepository.existsById(id)) {
            throw new EntityNotFoundException("Enquiry not found with id: " + id);
        }
        enquiryRepository.deleteById(id);
    }

    @Override
    public EnquiryEntry getById(Long id) throws Exception {
        return enquiryRepository.findById(id)
                .map(EnquiryConverter::toEntry)
                .orElseThrow(() -> new EntityNotFoundException("Enquiry not found with id: " + id));
    }

    @Override
    public List<EnquiryEntry> getAllEnquiries(Long branchId, Integer page, Integer size,
            Integer startMonth, Integer startYear,
            Integer endMonth, Integer endYear,
            String searchTerm) throws Exception {

        Pageable pageable = PageRequest.of(page, size, Sort.by("enquiryDate").descending());
        Specification<Enquiry> spec = Specification.where(EnquirySpecifications.hasBranchId(branchId));

        if (startMonth != null && startYear != null && endMonth != null && endYear != null) {
            Map<String, Date> monthRange = DateUtil.getDateRangeByMonthYear(startMonth, startYear, endMonth, endYear);
            spec = spec.and(EnquirySpecifications.enquiryDateBetween(monthRange.get("start"), monthRange.get("end")));
        }

        if (searchTerm != null && !searchTerm.isEmpty()) {
            spec = spec.and(EnquirySpecifications.searchTerm(searchTerm));
        }

        Page<Enquiry> pageResult = enquiryRepository.findAll(spec, pageable);

        return pageResult.stream()
                .map(EnquiryConverter::toEntry)
                .collect(Collectors.toList());
    }

    @Override
    public long countEnquiriesByBranchId(Long branchId) throws Exception {
        return enquiryRepository.count(EnquirySpecifications.hasBranchId(branchId));
    }

    @Override
    public long countEnquiriesByBranchIdAndMonth(Long branchId, Integer startMonth, Integer startYear,
            Integer endMonth, Integer endYear, String searchTerm) throws Exception {

        Map<String, Date> monthRange = DateUtil.getDateRangeByMonthYear(startMonth, startYear, endMonth, endYear);
        Specification<Enquiry> spec = Specification
                .where(EnquirySpecifications.hasBranchId(branchId))
                .and(EnquirySpecifications.enquiryDateBetween(monthRange.get("start"), monthRange.get("end")))
                .and(EnquirySpecifications.searchTerm(searchTerm));

        return enquiryRepository.count(spec);
    }
}
