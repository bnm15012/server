package com.dancestudio.erp.modules.member.memberActiveStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

@Entity
@Data
@Table(name = "active_period")
@NoArgsConstructor
@AllArgsConstructor
public class ActivePeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_active_status_id", nullable = false)
    @JsonIgnore
    private MemberActiveStatus memberActiveStatus;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    public ActivePeriod(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ActivePeriod(MemberActiveStatus memberActiveStatus, Date startDate, Date endDate) {
        this.memberActiveStatus = memberActiveStatus;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
