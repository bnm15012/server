package com.dancestudio.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "bank_accounts")
public class BankAccount extends BaseEntity {

    private String accountNumber;
    private String bankName;
    private String branchName;
    private String ifscCode;

    @OneToOne
    @JoinColumn(name = "instructor_id", referencedColumnName = "id", unique = true)
    private Instructor instructor;

}