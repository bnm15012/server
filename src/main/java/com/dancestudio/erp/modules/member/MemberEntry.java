package com.dancestudio.erp.modules.member;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberEntry {
    private String name;
    private String email;
    private String phone;
    private LocalDate dob;
    private String profileImage;
    private String address;
    private String emergencyContactNumber;
    private String memberType;
    private Long branchId;
    private List<MemberActivityAssignmentEntry> assignments;
}
