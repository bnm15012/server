package com.dancestudio.erp.entity;

import com.dancestudio.erp.util.DateUtil;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Subscription extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_subscription_studio_id"))
    private Studio studio;

    @Column(name = "subscription_plan", nullable = false)
    private String subscriptionPlan; // e.g., "Monthly", "Yearly", "Half-Yearly"

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date endDate;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "order_id", nullable = false)
    private String orderId;
    
    @Column(name = "payment_id")
    private String paymentId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "renewal_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date renewalDate;
    
    public boolean isExpired() {
        return DateUtil.getCurrentDateUTC().after(this.endDate);
    }
}
