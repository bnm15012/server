package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.converter.EnquiryConverter;
import com.dancestudio.erp.entity.Enquiry;
import com.dancestudio.erp.entry.EnquiryEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.EnquiryManager;
import com.dancestudio.erp.repository.EnquiryRepository;
import com.dancestudio.erp.util.DateUtil;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Setter(onMethod = @__({ @Autowired }))
@Component
public class EnquiryManagerImpl implements EnquiryManager {

    private EnquiryRepository enquiryRepository;

    @Override
    public EnquiryEntry add(EnquiryEntry entry) throws Exception {
        Enquiry entity = EnquiryConverter.toEntity(entry, null);
        Enquiry saved = enquiryRepository.save(entity);
        return EnquiryConverter.toEntry(saved);
    }

    @Override
    public EnquiryEntry update(Long id, EnquiryEntry entry) throws Exception {
        Optional<Enquiry> existingOpt = enquiryRepository.findById(id);
        if (existingOpt.isEmpty()) {
            throw new EntityNotFoundException("Enquiry not found with id: " + id);
        }

        Enquiry existing = existingOpt.get();
        Enquiry updated = enquiryRepository.save(EnquiryConverter.toEntity(entry, existing));
        return EnquiryConverter.toEntry(updated);
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

        Page<Enquiry> pageResult;
        Pageable pageable = PageRequest.of(page, size, Sort.by("enquiryDate").descending());

        if (Objects.nonNull(startMonth) && Objects.nonNull(startMonth) && Objects.nonNull(startYear)
                && Objects.nonNull(endMonth) && Objects.nonNull(endMonth) && Objects.nonNull(endYear)) {

            Map<String, Date> monthRange = DateUtil.getDateRangeByMonthYear(startMonth, startYear, endMonth, endYear);
            String term = (searchTerm != null) ? searchTerm : "";

            pageResult = enquiryRepository.findByBranchIdAndEnquiryDateBetweenAndNameContainingIgnoreCase(
                    branchId, monthRange.get("start"), monthRange.get("end"), term, pageable);
        } else {
            String term = (searchTerm != null) ? searchTerm : "";
            pageResult = enquiryRepository.findByBranchId(branchId, pageable, term);// .getContent();
        }
        return pageResult.stream()
                .map(EnquiryConverter::toEntry)
                .collect(Collectors.toList());
    }

    @Override
    public long countEnquiriesByBranchId(Long branchId) throws Exception {
        return enquiryRepository.countByBranchId(branchId);
    }

    @Override
    public long countEnquiriesByBranchIdAndMonth(Long branchId, Integer startMonth, Integer startYear,
            Integer endMonth, Integer endYear, String searchTerm) throws Exception {

        Map<String, Date> monthRange = DateUtil.getDateRangeByMonthYear(startMonth, startYear, endMonth, endYear);
        String term = (searchTerm != null) ? searchTerm : "";
        return enquiryRepository.countByBranchIdAndEnquiryDateBetweenAndNameContainingIgnoreCase(
                branchId, monthRange.get("start"), monthRange.get("end"), term);
    }
}
