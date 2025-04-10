package com.dancestudio.erp.entry;

import com.dancestudio.erp.enums.MembershipType;
import lombok.Data;

@Data
public class PlanEntry {

    private Long id;
    private MembershipType planType;
    private Double amount;
    private String description;
}
