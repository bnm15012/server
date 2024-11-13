package com.dancestudio.erp.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "instructor", uniqueConstraints = {
        @UniqueConstraint(name = "name_email_key", columnNames = {"name", "email"})
})
public class Instructor extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false, length = 10)
    private String phone;

    private String profileImage;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "bank_account_id", nullable = true)
    private Long bankAccountId;

    @Column(name = "studio_id", nullable = false)
    private Long studioId;

}

