package com.dancestudio.erp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class StudentActivityAssignment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @Column(name = "activity_id", nullable = false)
    private Long activityId;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Column(name = "membership_start_date", nullable = false)
    private LocalDate membershipStartDate;

    @Column(name = "membership_end_date", nullable = false)
    private LocalDate membershipEndDate;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "membership_type", nullable = false)
    private String membershipType;
}
