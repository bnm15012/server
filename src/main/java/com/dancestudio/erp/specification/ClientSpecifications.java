package com.dancestudio.erp.specification;

import com.dancestudio.erp.entity.Client;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class ClientSpecifications {

    public static Specification<Client> hasBranchId(Long branchId) {
        return (root, query, cb) ->
                branchId == null ? null : cb.equal(root.get("branch").get("id"), branchId);
    }

    public static Specification<Client> createdBetween(Date startDate, Date endDate) {
        return (root, query, cb) -> {
            if (startDate != null && endDate != null) {
                return cb.between(root.get("createdOn"), startDate, endDate);
            } else if (startDate != null) {
                return cb.greaterThanOrEqualTo(root.get("createdOn"), startDate);
            } else if (endDate != null) {
                return cb.lessThanOrEqualTo(root.get("createdOn"), endDate);
            }
            return null;
        };
    }

    public static Specification<Client> searchByText(String search) {
        return (root, query, cb) -> {
            if (search == null || search.trim().isEmpty()) {
                return null;
            }
            String likePattern = "%" + search.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("groupName")), likePattern),
                    cb.like(cb.lower(root.get("pocName")), likePattern),
                    cb.like(cb.lower(root.get("pocPhone")), likePattern),
                    cb.like(cb.lower(root.get("pocEmail")), likePattern)
            );
        };
    }
}
