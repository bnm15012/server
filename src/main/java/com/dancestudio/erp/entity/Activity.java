package com.dancestudio.erp.entity;

import com.dancestudio.erp.enums.ActivityType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Activity extends BaseEntity {

    private ActivityType activityType;
    private String description;

    @ManyToOne
    @JoinColumn(name = "studio_id")
    private Studio studio;
}
