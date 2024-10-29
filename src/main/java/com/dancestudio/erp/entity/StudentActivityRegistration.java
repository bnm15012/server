package com.dancestudio.erp.entity;

import com.dancestudio.erp.enums.MembershipStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class StudentActivityRegistration extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    private LocalDate registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MembershipStatus status;

}
