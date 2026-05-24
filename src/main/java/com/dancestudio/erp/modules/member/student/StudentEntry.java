package com.dancestudio.erp.modules.member.student;


import com.dancestudio.erp.enums.GenderType;
import com.dancestudio.erp.enums.MembershipStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentEntry {

    private Long studentId;
    private String name;
    private String email;
    private String phone;
    private LocalDate dob;
    private String imageUrl;
    private String address;
    private String emergencyContactNumber;
    private MembershipStatus membershipStatus;
    private Long branchId;
    private GenderType gender;
    private Long activeMembershipCount;

}
