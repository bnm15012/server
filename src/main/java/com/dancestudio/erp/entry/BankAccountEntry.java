package com.dancestudio.erp.entry;

import lombok.Data;

@Data
public class BankAccountEntry {

    private Long bankAccountId;
    private String accountNumber;
    private String bankName;
    private String branchName;
    private String ifscCode;
    private String upiId;

    private Long instructorId;
}
