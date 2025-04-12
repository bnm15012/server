package com.dancestudio.erp.entry;

import com.dancestudio.erp.enums.MembershipType;
import lombok.Data;

import java.util.List;

@Data
public class PlanEntry {

    private Long id;
    private MembershipType planType;
    private String amount;
    private List<String> enabledFeatures;
    private List<String> disabledFeatures;
    private String countryCode;
}
