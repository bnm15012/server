package com.dancestudio.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Studio extends BaseEntity {

    private String studioName;

    private String location;

    private String logo;

    private String contactDetails;

    @OneToMany(mappedBy = "studio", cascade = CascadeType.ALL)
    private List<Instructor> instructors;

    @OneToMany(mappedBy = "studio", cascade = CascadeType.ALL)
    private List<Student> students;

    @OneToMany(mappedBy = "studio", cascade = CascadeType.ALL)
    private List<Activity> activities;

}
