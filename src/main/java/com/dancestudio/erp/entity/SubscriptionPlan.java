package com.dancestudio.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class SubscriptionPlan extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_subscription_studio_id"))
    private Studio studio;

    @Column(name = "subscription_plan", nullable = false)
    private String subscriptionPlan; // e.g., "Monthly", "Yearly", "Half-Yearly"

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "renewal_date")
    private Date renewalDate;
    
    public boolean isExpired() {
        return new Date().after(this.endDate);
    }
}
