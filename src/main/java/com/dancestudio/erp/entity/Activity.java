package com.dancestudio.erp.entity;

import com.dancestudio.erp.enums.ActivityType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Activity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "activityType", nullable = false)
    private ActivityType activityType;

    private String description;

    @ManyToOne
    @JoinColumn(name = "studio_id")
    private Studio studio;
}
