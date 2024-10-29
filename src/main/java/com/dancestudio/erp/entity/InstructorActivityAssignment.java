package com.dancestudio.erp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;

@Data
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
