package com.dancestudio.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class StudentActivityAssignment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "activity_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_saa_activity_id"))
    private Activity activity;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Column(name = "membership_start_date", nullable = false)
    private LocalDate membershipStartDate;

    @Column(name = "membership_end_date", nullable = false)
    private LocalDate membershipEndDate;

    @Column(name = "membership_type", nullable = false)
    private String membershipType;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_student_id"))
    private Student student;
}
