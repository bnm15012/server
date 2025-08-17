package com.dancestudio.erp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "studio", uniqueConstraints = {
        @UniqueConstraint(name = "name_location_key", columnNames = {"name", "location"})
})
public class Studio extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "logo")
    private String logo;

    @Column(name = "email")
    private String email;

    @Column(name = "pass_code")
    private String passcode;

    @Column(name = "contact")
    private String contactDetails;

    @Column(name = "gst_number")
    private String gstNumber;

    @Column(name = "configuration", columnDefinition = "json")
    private String configuration;

}
