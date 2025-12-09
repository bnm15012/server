package com.dancestudio.erp.entry;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentExpenseSummary {
    private Long count;        
    private Double totalAmount; 

    public PaymentExpenseSummary() {
        this.count = 0L;
        this.totalAmount = 0.0;
    }
}
