package com.dancestudio.erp.modules.member.instructor;

import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.modules.member.instructor.bankAccount.BankAccountEntry;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InstructorEntry {

    private Long instructorId;
    private String name;
    private String email;
    private String phone;
    private LocalDate dob;
    private String imageUrl;
    private String address;
    private String emergencyContactNumber;
    private MembershipStatus instructorStatus;
    private BankAccountEntry bankAccountDetails;
    private Long branchId;
}
