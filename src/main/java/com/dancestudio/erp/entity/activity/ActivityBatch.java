package com.dancestudio.erp.entity.activity;

import com.dancestudio.erp.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "activity_batch", uniqueConstraints = {
        @UniqueConstraint(name = "plan_type_activity_id_days_per_week_key", columnNames = { "plan_type", "activity_id",
                "days_per_week" })
})
public class ActivityBatch extends BaseEntity {

    @Column(name = "plan_type", nullable = false)
    private String planType;

    @Column(name = "days_per_week")
    private Integer daysPerWeek;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "start_time", nullable = false)
    private String startTime;

    @Column(name = "end_time", nullable = false)
    private String endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id", nullable = false, foreignKey = @ForeignKey(name = "fk_activity_activity_id"))
    private Activity activity;
}
