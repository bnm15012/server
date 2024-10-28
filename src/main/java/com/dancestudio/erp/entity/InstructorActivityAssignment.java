package com.dancestudio.erp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

@Entity
public class InstructorActivityAssignment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    private LocalDate assignedDate;
}
