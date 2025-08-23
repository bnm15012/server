package com.dancestudio.erp.entry;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnquiryEntry {
    private Long enquiryId;
    private Long branchId;
    private String name;
    private String contact;
    private String enquiryPurpose;
    private Date enquiryDate;
}
