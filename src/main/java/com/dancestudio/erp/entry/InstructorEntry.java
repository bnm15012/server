package com.dancestudio.erp.entry;

import lombok.Data;

@Data
public class InstructorEntry {

    private Long instructorId;
    private String name;
    private String email;
    private String phone;
    private String profileDetails;
    private BankAccountEntry bankAccountDetails;
    private Long studioId;
}
