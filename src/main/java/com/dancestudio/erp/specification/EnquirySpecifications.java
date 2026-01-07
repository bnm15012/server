package com.dancestudio.erp.specification;

import org.springframework.data.jpa.domain.Specification;

import com.dancestudio.erp.modules.enquiry.Enquiry;

import java.util.Date;

public class EnquirySpecifications {

    public static Specification<Enquiry> hasBranchId(Long branchId) {
        return (root, query, cb) -> cb.equal(root.get("branch").get("id"), branchId);
    }

    public static Specification<Enquiry> enquiryDateBetween(Date start, Date end) {
        return (root, query, cb) -> cb.between(root.get("enquiryDate"), start, end);
    }

    public static Specification<Enquiry> searchTerm(String searchTerm) {
        return (root, query, cb) -> {
            String likeTerm = "%" + searchTerm.toLowerCase() + "%";
            return cb.or(
                cb.like(cb.lower(root.get("name")), likeTerm),
                cb.like(cb.lower(root.get("contact")), likeTerm),
                cb.like(cb.lower(root.get("enquiryPurpose")), likeTerm)
            );
        };
    }
}
