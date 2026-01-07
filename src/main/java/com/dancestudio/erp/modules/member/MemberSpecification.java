package com.dancestudio.erp.modules.member;


import org.springframework.data.jpa.domain.Specification;

import com.dancestudio.erp.modules.member.student.StudentActivityAssignment.StudentActivityAssignment;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

// TODO: use this 
public class MemberSpecification {
    public static Specification<Member> hasAssignmentStatus(Long branchId, String membershipType, String status,
            String name, String email, String phone) {
        return (root, query, cb) -> {
            query.distinct(true);

            Join<Member, StudentActivityAssignment> saa = root.join("studentActivityAssignments", JoinType.LEFT);

            query.groupBy(root.get("id"));

            var activeCount = cb.sum(
                    cb.<Integer>selectCase()
                            .when(cb.and(
                                    cb.lessThan(saa.get("membershipStartDate"), cb.currentDate()),
                                    cb.greaterThan(saa.get("membershipEndDate"), cb.currentDate())), 1)
                            .otherwise(0));

            if ("ACTIVE".equalsIgnoreCase(status)) {
                query.having(cb.greaterThan(activeCount, 0));
            } else if ("INACTIVE".equalsIgnoreCase(status)) {
                query.having(cb.equal(activeCount, 0));
            }

            Predicate p = cb.conjunction();
            cb.equal(root.get("branch").get("id"), branchId);
            cb.equal(root.get("memberType"), "STUDENT");
            if (name != null && !name.isEmpty()) {
                p = cb.and(p, cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (email != null && !email.isEmpty()) {
                p = cb.and(p, cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
            }
            if (phone != null && !phone.isEmpty()) {
                p = cb.and(p, cb.like(root.get("phone"), "%" + phone + "%"));
            }

            return p;
        };
    }
}
