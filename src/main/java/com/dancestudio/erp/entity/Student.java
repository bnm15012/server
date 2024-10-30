package com.dancestudio.erp.entity;

import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.enums.MembershipType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Student extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false, length = 10)
    private String phone;

    private String profileDetails;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MembershipStatus status;

    @Enumerated(EnumType.STRING)
    private MembershipType membershipType;

    private LocalDate membershipStartDate;

    private LocalDate membershipEndDate;

    @Column(name = "email_sent", columnDefinition = "boolean default false")
    private boolean emailSent;

    @ManyToOne
    @JoinColumn(name = "studio_id", nullable = true)
    private Studio studio;
}
