package com.dancestudio.erp.entry;

import com.dancestudio.erp.enums.MembershipStatus;
import lombok.Data;

import java.util.Date;

@Data
public class InstructorEntry {

    private Long instructorId;
    private String name;
    private String email;
    private String phone;
    private Date dob;
    private String imageUrl;
    private String address;
    private String emergencyContactNumber;
    private MembershipStatus instructorStatus;
    private BankAccountEntry bankAccountDetails;
    private Long branchId;
}
