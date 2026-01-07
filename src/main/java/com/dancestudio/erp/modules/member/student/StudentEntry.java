package com.dancestudio.erp.modules.member.student;


import com.dancestudio.erp.enums.MembershipStatus;
import lombok.Data;

import java.util.Date;

@Data
public class StudentEntry {

    private Long studentId;
    private String name;
    private String email;
    private String phone;
    private Date dob;
    private String imageUrl;
    private String address;
    private String emergencyContactNumber;
    private MembershipStatus membershipStatus;
    private Long branchId;
    private Long activeMembershipCount;

}
