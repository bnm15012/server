package com.dancestudio.erp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Activity extends BaseEntity {

    @Column(name = "activityType", nullable = false)
    private String activityType;

    private String description;

    @Column(name = "studio_id", nullable = false)
    private Long studioId;
}
