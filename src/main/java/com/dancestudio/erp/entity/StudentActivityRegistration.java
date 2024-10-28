package com.dancestudio.erp.entity;

import com.dancestudio.erp.enums.MembershipStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

@Entity
public class StudentActivityRegistration extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    private LocalDate registrationDate;
    private MembershipStatus status;

}
