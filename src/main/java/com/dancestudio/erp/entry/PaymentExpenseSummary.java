package com.dancestudio.erp.entry;

import lombok.Data;

@Data
public class PaymentExpenseSummary {
    private long count;
    private double totalAmount;

    public PaymentExpenseSummary(long count, double totalAmount) {
        this.count = count;
        this.totalAmount = totalAmount;
    }

    public PaymentExpenseSummary() {
        this.count = 0L;
        this.totalAmount = 0.0;
    }
}
