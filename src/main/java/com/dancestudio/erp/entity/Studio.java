package com.dancestudio.erp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Studio extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "logo")
    private String logo;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "contact")
    private String contactDetails;

}
