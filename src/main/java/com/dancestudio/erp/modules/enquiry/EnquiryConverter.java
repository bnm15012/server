package com.dancestudio.erp.modules.enquiry;

import com.dancestudio.erp.repository.BranchRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class EnquiryConverter {

    private static ApplicationContext applicationContext;

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        applicationContext = context;
    }

    public static Enquiry toEntity(EnquiryEntry entry, Enquiry existEnquiry) throws Exception {
        Enquiry enquiry = existEnquiry != null ? existEnquiry : new Enquiry();
        if (Objects.nonNull(entry.getEnquiryId())) {
            enquiry.setId(entry.getEnquiryId());
        }
        if (Objects.nonNull(entry.getName())) {
            enquiry.setName(entry.getName());
        }
        if (Objects.nonNull(entry.getEnquiryDate())) {
            enquiry.setEnquiryDate(entry.getEnquiryDate());
        }
        if (Objects.nonNull(entry.getContact())) {
            enquiry.setContact(entry.getContact());
        }
        if (Objects.nonNull(entry.getEnquiryPurpose())) {
            enquiry.setEnquiryPurpose(entry.getEnquiryPurpose());
        }
        if (Objects.nonNull(entry.getBranchId())) {
            BranchRepository branchRepository = applicationContext.getBean(BranchRepository.class);
            enquiry.setBranch(branchRepository.findById(entry.getBranchId()).orElseThrow(
                    () -> new EntityNotFoundException("Branch not found with id: " + entry.getBranchId() + "")));
        }
        return enquiry;
    }

    public static EnquiryEntry toEntry(Enquiry entity) {
        EnquiryEntry entry = new EnquiryEntry();
        entry.setName(entity.getName());
        entry.setContact(entity.getContact());
        entry.setEnquiryPurpose(entity.getEnquiryPurpose());
        entry.setEnquiryDate(entity.getEnquiryDate());
        entry.setEnquiryId(entity.getId());
        entry.setBranchId(entity.getBranch().getId());

        return entry;
    }
}
