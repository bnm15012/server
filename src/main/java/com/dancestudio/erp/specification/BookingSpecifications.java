package com.dancestudio.erp.specification;

import org.springframework.data.jpa.domain.Specification;

import com.dancestudio.erp.modules.booking.Booking;
import com.dancestudio.erp.modules.payments.entity.Payment;
import com.dancestudio.erp.modules.payments.entity.PaymentBooking;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

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
                    cb.like(cb.lower(root.get("client").get("pocName")), like));
        };
    }

    public static Specification<Booking> byPaymentType(String paymentType) {
        return (root, query, cb) -> {
            if (paymentType == null
                    || paymentType.trim().isEmpty()
                    || "ALL".equalsIgnoreCase(paymentType)) {
                return cb.conjunction();
            }

            query.distinct(true);

            Join<Booking, PaymentBooking> paymentBookingJoin = root.join("payments", JoinType.INNER);

            Join<PaymentBooking, Payment> paymentJoin = paymentBookingJoin.join("payment", JoinType.INNER);

            return cb.equal(
                    cb.lower(paymentJoin.get("paymentType")),
                    paymentType.toLowerCase());
        };
    }
}
