package com.dancestudio.erp.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Instructor extends BaseEntity {
    private String name;
    private String email;
    private String phone;
    private String profileDetails;

    @OneToOne(mappedBy = "instructor", cascade = CascadeType.ALL, optional = true)
    private BankAccount bankAccount;

    @ManyToOne
    @JoinColumn(name = "studio_id", nullable = true)
    private Studio studio;

    @OneToMany(mappedBy = "instructor")
    private List<InstructorActivityAssignment> assignments;

}

