package com.dancestudio.erp.entity;

import com.dancestudio.erp.enums.MembershipStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Student extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    private String profileDetails;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "membership_status")
    private MembershipStatus membershipStatus;

    @Column(name = "email_sent", columnDefinition = "boolean default false")
    private boolean emailSent;

    @ManyToOne
    @JoinColumn(name = "studio_id")
    private Studio studio;
}
