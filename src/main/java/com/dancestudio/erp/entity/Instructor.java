package com.dancestudio.erp.entity;


import com.dancestudio.erp.enums.MembershipStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Instructor extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false, length = 10)
    private String phone;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MembershipStatus status;

    @OneToOne(mappedBy = "instructor", cascade = CascadeType.ALL, optional = true)
    @ToString.Exclude
    private BankAccount bankAccount;

    @ManyToOne
    @JoinColumn(name = "studio_id", nullable = true)
    @ToString.Exclude
    private Studio studio;

    @OneToMany(mappedBy = "instructor")
    @ToString.Exclude
    private List<InstructorActivityAssignment> assignments;

}

