package com.dancestudio.erp.modules.membershipPackage;

import lombok.Data;

@Data
public class MembershipPackagesEntry {
    private Long id;
    private String membershipPackage;
    private Long studioId;
}
