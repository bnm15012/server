package com.dancestudio.erp.entry;


import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.enums.MembershipType;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class StudentEntry {

    private Long studentId;
    private String name;
    private String email;
    private String phone;
    private String imageUrl;
    private LocalDate registrationDate;
    private MembershipStatus membershipStatus;
    private LocalDate membershipStartDate;
    private LocalDate membershipEndDate;
    private MembershipType membershipType;
    private Long studioId;
    private List<String> enrolledActivities;

}
