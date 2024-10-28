package com.dancestudio.erp.entry;


import com.dancestudio.erp.enums.MembershipStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentEntry {

    private Long studentId;
    private String name;
    private String email;
    private String phone;
    private String profileDetails;
    private LocalDate registrationDate;
    private MembershipStatus membershipStatus;
    private Long studioId;

}
