package com.dancestudio.erp.entry;

import lombok.Data;

import java.util.List;

@Data
public class BranchEntry {

    private Long branchId;
    private Long studioId;
    private String name;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private String phone;
    private Boolean isActive;

    private List<UserEntry> userEntries;

}
