package com.dancestudio.erp.entry;

import lombok.Data;

@Data
public class PasswordEntry {

    private String otpToken;
    private Long tokenValidity;

    private boolean isValid = false;

}
