package com.dancestudio.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "studio_sms_usage", uniqueConstraints = {
        @UniqueConstraint(name = "uq_branch_month", columnNames = {"branch_id", "month"})
})
@EqualsAndHashCode(callSuper = true)
public class StudioSmsUsage extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false, foreignKey = @ForeignKey(name = "fk_sms_usage_branch_id"))
    private Branch branch;

    @Column(name = "month", nullable = false)
    private Long month;

    @Column(name = "total_sms_sent", nullable = false, columnDefinition = "bigint default 0")
    private Long totalSmsSent;

    @Column(name = "quota", nullable = false,  columnDefinition = "bigint default 0")
    private Long quota;
}
