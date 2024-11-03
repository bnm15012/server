package com.dancestudio.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Studio extends BaseEntity {

    private String name;

    private String location;

    private String logo;

    @Column(name = "contact")
    private String contactDetails;

    @OneToMany(mappedBy = "studio", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Instructor> instructors;

    @OneToMany(mappedBy = "studio", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Student> students;

    @OneToMany(mappedBy = "studio", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Activity> activities;

}
