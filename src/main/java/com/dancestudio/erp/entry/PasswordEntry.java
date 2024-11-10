package com.dancestudio.erp.entry;

import lombok.Data;

@Data
public class PasswordEntry {

    private String otpToken;
    private String otp;
    private UserEntry userEntry;
    private Long tokenValidity;

    private boolean isValid = false;

}
