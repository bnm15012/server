package com.dancestudio.erp.entry;

import com.dancestudio.erp.enums.MembershipType;
import lombok.Data;

@Data
public class MembershipPlanEntry {

    private MembershipType membershipType;
    private Double amount;
    private Integer daysPerWeek;

}
