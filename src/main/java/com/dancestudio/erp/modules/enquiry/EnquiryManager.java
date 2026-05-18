package com.dancestudio.erp.modules.enquiry;

import com.dancestudio.erp.base.BaseManager;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.repository.EnquiryRepository;
import com.dancestudio.erp.specification.EnquirySpecifications;
import com.dancestudio.erp.util.DateUtil;

import lombok.Setter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Setter(onMethod = @__({ @Autowired }))
@Component
public class EnquiryManager extends BaseManager<Enquiry, Long, EnquiryEntry> {

    private EnquiryRepository enquiryRepository;

    protected EnquiryManager(EnquiryRepository repository) {
        super(repository, "enquiry");
        this.enquiryRepository = repository;
    }

    public Page<EnquiryEntry> getAllEnquiries(Long branchId, Integer page, Integer size,
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

        return pageResult.map(EnquiryConverter::toEntry);
    }

    @Override
    protected Enquiry toEntity(EnquiryEntry entry, Enquiry existing)
            throws EntityNotFoundException, BeansException, Exception {
        return EnquiryConverter.toEntity(entry, existing);
    }

    @Override
    protected EnquiryEntry toEntry(Enquiry entity, String[] fields) throws EntityNotFoundException {
        return EnquiryConverter.toEntry(entity);
    }
}
