package com.dancestudio.erp.entity;

import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.enums.MembershipType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;
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

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MembershipStatus status;

    @Enumerated(EnumType.STRING)
    private MembershipType membershipType;

    private LocalDate membershipStartDate;

    private LocalDate membershipEndDate;

    @OneToMany(mappedBy = "student")
    private List<StudentActivityAssignment> enrolledActivities;

    @ManyToOne
    @JoinColumn(name = "studio_id", nullable = true)
    private Studio studio;

    public List<String> getEnrolledActivityNames() {
        return enrolledActivities.stream()
                .map(assignment -> assignment.getActivity().getActivityType().name()) // Assuming activityType is an Enum
                .collect(Collectors.toList());
    }
}
