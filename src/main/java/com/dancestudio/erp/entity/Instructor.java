package com.dancestudio.erp.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "bank_acocunt_id", nullable = true)
    private Long bankAccountId;

    @Column(name = "studio_id", nullable = false)
    private Long studioId;

    @OneToMany(mappedBy = "instructor")
    @ToString.Exclude
    private List<InstructorActivityAssignment> assignments;

}

