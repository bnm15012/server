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

    public static Specification<Payment> byPaymentType(PaymentType paymentType) {
        return (root, query, cb) -> {
            if (paymentType == null || paymentType.equals(PaymentType.ALL)) {
                return null;
            }

            return cb.equal(root.get("paymentType"), paymentType);
        };
    }

    public static Specification<Payment> bySearchTerm(String searchTerm) {
        return (root, query, cb) -> {
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                return null;
            }

            String pattern = "%" + searchTerm.toLowerCase() + "%";

            var bookingJoin = root.join("paymentBooking", JoinType.LEFT);
            var clientJoin = bookingJoin.join("booking", JoinType.LEFT).join("client", JoinType.LEFT);

            var studentActivityJoin = root.join("paymentStudentActivity", JoinType.LEFT);
            var studentJoin = studentActivityJoin.join("studentActivityAssignment", JoinType.LEFT).join("student", JoinType.LEFT);

            return cb.or(
                cb.like(cb.lower(clientJoin.get("pocName")), pattern),
                cb.like(cb.lower(studentJoin.get("name")), pattern)
            );
        };
    }
}
