package com.dancestudio.erp.specification;
import com.dancestudio.erp.entity.Booking;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class BookingSpecifications {

    public static Specification<Booking> hasBranchId(Long branchId) {
        return (root, query, cb) ->
                cb.equal(root.get("branch").get("id"), branchId);
    }

    public static Specification<Booking> createdOnBetween(Date startDate, Date endDate) {
        return (root, query, cb) ->
                cb.between(root.get("createdOn"), startDate, endDate);
    }

    public static Specification<Booking> search(String term) {
        return (root, query, cb) -> {
            if (term == null || term.trim().isEmpty()) {
                return cb.conjunction();
            }
            String like = "%" + term.toLowerCase() + "%";
            return cb.or(
                cb.like(cb.lower(root.get("purpose")), like),
                cb.like(cb.lower(root.get("client").get("pocName")), like)
            );
        };
    }
}
