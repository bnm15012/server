package com.dancestudio.erp.modules.payments.spec;

import java.util.Date;

import org.springframework.data.jpa.domain.Specification;

import com.dancestudio.erp.enums.PaymentStatus;
import com.dancestudio.erp.enums.PaymentType;
import com.dancestudio.erp.modules.payments.entity.Payment;

import jakarta.persistence.criteria.JoinType;

public class PaymentSpecification {

    public static Specification<Payment> withJoins() {
        return (root, query, cb) -> {
            query.distinct(true);
            root.join("paymentBooking", JoinType.LEFT);
            root.join("paymentStudentActivity", JoinType.LEFT);
            return cb.conjunction();
        };
    }

    public static Specification<Payment> byBranch(Long branchId) {
        return (root, query, cb) -> 
            branchId == null ? null : cb.equal(root.get("branch").get("id"), branchId);
    }

    public static Specification<Payment> byStatus(PaymentStatus status) {
        return (root, query, cb) -> 
            status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<Payment> byDateRange(Date from, Date to) {
        return (root, query, cb) -> {
            if (from != null && to != null)
                return cb.between(root.get("paymentDate"), from, to);

            if (from != null)
                return cb.greaterThanOrEqualTo(root.get("paymentDate"), from);

            if (to != null)
                return cb.lessThanOrEqualTo(root.get("paymentDate"), to);

            return null;
        };
    }

    public static Specification<Payment> byPaymentType(String paymentType) {
        return (root, query, cb) -> {
            if (paymentType == null || paymentType.equalsIgnoreCase("all")) {
                return null;
            }

            return cb.equal(root.get("paymentType"), PaymentType.valueOf(paymentType.toUpperCase()));
        };
    }
}
