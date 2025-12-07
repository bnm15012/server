package com.dancestudio.erp.modules.plan;

import com.dancestudio.erp.enums.MembershipType;
import lombok.Data;

import java.util.List;

@Data
public class PlanEntry {

    private Long id;
    private MembershipType planType;
    private Double amount;
    private List<String> enabledFeatures;
    private List<String> disabledFeatures;
    private Long smsQuota;
    private String countryCode;
    private String description;
    private Boolean popular;
    private Integer remindBeforeDays;
}
