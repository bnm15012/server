package com.dancestudio.erp.entry;

import com.dancestudio.erp.enums.MembershipStatus;
import lombok.Data;

import java.util.List;

@Data
public class InstructorEntry {

    private Long instructorId;
    private String name;
    private String email;
    private String phone;
    private String imageUrl;
    private MembershipStatus instructorStatus;
    private BankAccountEntry bankAccountDetails;
    private StudioEntry studioEntry;

    private List<InstructorActivityAssignmentEntry> assignments;
}
