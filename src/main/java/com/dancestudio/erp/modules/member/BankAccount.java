package com.dancestudio.erp.modules.member;

import com.dancestudio.erp.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "bank_account")
public class BankAccount extends BaseEntity {

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @Column(name = "upi_id")
    private String upiId;

    @Column(name = "instructor_id")
    private Long instructorId;

}