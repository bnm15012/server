package com.dancestudio.erp.entry;

import lombok.Data;

import java.util.List;
import com.dancestudio.erp.enums.WhatsAppStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    private WhatsAppStatus whatsAppStatus; 

    private List<UserEntry> userEntries;

}
