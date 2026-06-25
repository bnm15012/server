package com.dancestudio.erp.modules.expense.spec;

import com.dancestudio.erp.enums.PaymentType;
import com.dancestudio.erp.modules.expense.Expense;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class ExpenseSpecification {

    public static Specification<Expense> byBranch(Long branchId) {
        return (root, query, cb) -> branchId == null ? null : cb.equal(root.get("branch").get("id"), branchId);
    }

    public static Specification<Expense> byDateRange(Date from, Date to) {
        return (root, query, cb) -> {
            if (from != null && to != null)
                return cb.between(root.get("expenseDate"), from, to);

            if (from != null)
                return cb.greaterThanOrEqualTo(root.get("expenseDate"), from);

            if (to != null)
                return cb.lessThanOrEqualTo(root.get("expenseDate"), to);

            return null;
        };
    }

    public static Specification<Expense> bySearchTerm(String searchTerm) {
        return (root, query, cb) -> {
            if (searchTerm == null || searchTerm.isBlank())
                return null;
            return cb.like(cb.lower(root.get("description")), "%" + searchTerm.toLowerCase() + "%");
        };
    }

    public static Specification<Expense> byPaymentType(String paymentType) {
        return (root, query, cb) -> {
            if (paymentType == null || paymentType.isBlank() || paymentType.equalsIgnoreCase("ALL"))
                return null;
            return cb.equal(root.get("paymentType"), PaymentType.valueOf(paymentType));
        };
    }
}
