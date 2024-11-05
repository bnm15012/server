package com.dancestudio.erp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    private String profileImage;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "membership_type", nullable = false)
    private String membershipType;

    @Column(name = "membership_start_date", nullable = false)
    private LocalDate membershipStartDate;

    @Column(name = "membership_end_date", nullable = false)
    private LocalDate membershipEndDate;

    @OneToMany(mappedBy = "student")
    private List<StudentActivityAssignment> enrolledActivities;

    @Column(name = "studio_id", nullable = false)
    private Long studioId;

    public List<String> getEnrolledActivityNames() {
        return Objects.nonNull(enrolledActivities) ? enrolledActivities.stream()
                .map(assignment -> assignment.getActivity().getActivityType())
                .collect(Collectors.toList()) : null;
    }
}
