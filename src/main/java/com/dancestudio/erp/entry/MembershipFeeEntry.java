package com.dancestudio.erp.entry;

import com.dancestudio.erp.enums.ActivityType;
import com.dancestudio.erp.enums.MembershipType;
import lombok.Data;

@Data
public class MembershipFeeEntry {

    private Long studioId;
    private MembershipType membershipType;
    private Double amount;
}
